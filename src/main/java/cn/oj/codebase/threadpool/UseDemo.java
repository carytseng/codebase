package cn.oj.codebase.threadpool;

import com.ejlchina.okhttps.OkHttps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: codebase
 * @description: 线程池创建使用实例
 * @author: 郑剑锋
 * @create: 2021-04-24 13:16
 **/
@Slf4j
public class UseDemo {

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("searchLog-pool-%d").build();

    ExecutorService scheduledThreadPool = new ThreadPoolExecutor(10, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    public void dosomething() {
        scheduledThreadPool.execute(() -> {

        });
    }

   /* public static void main(String[] args) {
        List<HttpCookie> cookies = HttpCookie.parse("tenant=1111; token=1212");
        String tenant = null;
        String token = null;
        getCookieByStr("tenant=1111;token=1212");
        if (cookies.size() > 0) {
            for (HttpCookie cookie : cookies) {
                if (cookie.getName().equals("tenant")) {
                    tenant = cookie.getValue();
                    System.out.println(tenant);
                }
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    System.out.println(token);
                }
            }
        }
    }*/

    /*public static void main(String[] args) {
        String publicKey = "044d5f65c2cdeac43e8a8d3c32c42ff012a3f0141b212059b0fb47b49c9c32140882ba177209e4a5d7f8699346659ff14c6def4278c6e2e9cf5b2a662b39d40cf6";
        SM2 sm2 = SmUtil.sm2(null, publicKey);
        String pwd = sm2.encryptBcd("123456", KeyType.PublicKey);
        System.out.println(pwd);
    }*/

    public static void main(String[] args) {

        GatewayRequest gatewayRequest = new GatewayRequest();
        gatewayRequest.setSsoUrl("aaaaa");
        gatewayRequest.setApplicationCode("ulink");
        gatewayRequest.setUmsToken("FumyQoQwghC137G1xhOR7rrul6d3VVLzRK4U0xFwDhTQU36WVoCKaUtUzoNq5NINl2Em6w5wJhslVBpQPfaIjJ39o56oF303JYKcNFXMZOnRaKAeZciSak8Cj10H5Wuf");
        GatewayResponse req = OkHttps.sync("http://localhost/sso/replaceToken")
                .bodyType(OkHttps.JSON)
                .setBodyPara(gatewayRequest)
                .post().getBody().toBean(GatewayResponse.class);
        System.out.println(req);
      /*  String req = OkHttps.sync("http://localhost/sso/replaceToken")
                .addUrlPara("ssoUrl", "111")
                .addUrlPara("token", "111")
                .addUrlPara("applicationCode", "111")
                .post().getBody().toString();
        String [] ss= "/demo/sss".split("/");
        System.out.println(ss[1]);*/
    }

    public static List<HttpCookie> getCookieByStr(String str) {
        String[] cookiestrs = str.split(";");
        List< HttpCookie> cookies =new ArrayList<>();
        for (int i = 0; i < cookiestrs.length; i++) {
            String[] onecookie = cookiestrs[i].split("=");
            cookies.add(new HttpCookie(onecookie[0], onecookie[1]));
        }
        return cookies;
    }
}
