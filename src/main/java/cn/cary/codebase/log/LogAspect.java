package cn.cary.codebase.log;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Aspect
@Slf4j
@Order(-98)
public class LogAspect {

    @Autowired
    HttpServletRequest request;
    /**
     * 【注意】 修改为你自己项目中controller层路径
     */
    @Pointcut("execution(public * cn.cary.codebase..controller..*.*(..))")
    public void pointcut() {
    }


    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //请求controller名称，使用@ControllerMethodTitle注解
        String controllerTitle = getControllerMethodTitle(joinPoint);
        //方法路径
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //IP地址
        String iP = getIp(request);
        //请求入参
        String requestParam = JSON.toJSONString(Arrays.stream(joinPoint.getArgs())
                .filter(param -> !(param instanceof HttpServletRequest)
                        && !(param instanceof HttpServletResponse)
                        && !(param instanceof MultipartFile)
                        && !(param instanceof MultipartFile[])
                ).collect(Collectors.toList()));

        log.info("\n    [Controller start], {}, methodName->{}, IP->{}, requestParam->{},", controllerTitle, methodName, iP, requestParam);

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("\n    [Controller end], {}, 耗时->{}ms, result->{}", controllerTitle, System.currentTimeMillis() - begin, JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 获取Controller的方法名
     * 可以使用我们自定义的@ControllerMethodTitle。若使用了swagger，就优先使用swagger的@ApiOperation的value
     */
    private String getControllerMethodTitle(ProceedingJoinPoint joinPoint) {
        Method[] methods = joinPoint.getSignature().getDeclaringType().getMethods();
        for (Method method : methods) {
            if (StringUtils.equalsIgnoreCase(method.getName(), joinPoint.getSignature().getName())) {
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                ControllerMethodTitle controllerMethodTitle = method.getAnnotation(ControllerMethodTitle.class);
                if (ObjectUtils.isNotEmpty(apiOperation) && StrUtil.isNotBlank(apiOperation.value())) {
                    return apiOperation.value();
                }
                if (ObjectUtils.isNotEmpty(controllerMethodTitle)) {
                    return controllerMethodTitle.value();
                }
            }
        }
        return "该Controller的方法使用未使用注解@ControllerMethodTitle，请使用该注解说明方法作用";
    }

    /**
     * 获取目标主机的ip
     *
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {
        List<String> ipHeadList = Stream.of("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "X-Real-IP").collect(Collectors.toList());
        for (String ipHead : ipHeadList) {
            if (checkIP(request.getHeader(ipHead))) {
                return request.getHeader(ipHead).split(",")[0];
            }
        }
        return "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()) ? "127.0.0.1" : request.getRemoteAddr();
    }

    /**
     * 检查ip存在
     */
    private boolean checkIP(String ip) {
        return !(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip));
    }


}