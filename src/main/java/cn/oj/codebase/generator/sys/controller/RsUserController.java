package cn.oj.codebase.generator.sys.controller;


import cn.oj.codebase.generator.dto.RsUserDTO;
import cn.oj.codebase.generator.sys.entity.RsUser;
import cn.oj.codebase.generator.sys.service.IRsUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 用户信息表（用于测试） 前端控制器
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-11
 */
@Slf4j
@Validated //验证非object对象需要
@RestController
@RequestMapping("/sys/rsUser")
@Tag(name = "用户信息")
public class RsUserController {

    @Autowired
    private IRsUserService iRsUserService;

    @GetMapping("/{id}")
    @Operation(summary = "查询")
    @Parameter(name = "id", description = "唯一标识", required = true)
    public Map<String, Object> get(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        RsUser user = iRsUserService.getById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", user);
        result.put("msg", "success");
        return result;
    }

    @PostMapping("/insert")
    @Operation(summary = "新增")
    public Map<String, Object> insert(@Valid RsUserDTO rsUserDTO) {
        RsUser rsUser = new RsUser();
        BeanUtils.copyProperties(rsUserDTO, rsUser);
        boolean success = iRsUserService.save(rsUser);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", success);
        result.put("msg", success ? "success" : "failed");
        return result;
    }

    @PutMapping("/update")
    @Operation(summary = "更新")
    public Map<String, Object> update(@Valid RsUserDTO rsUserDTO) {
        RsUser rsUser = new RsUser();
        BeanUtils.copyProperties(rsUserDTO, rsUser);
        boolean success = iRsUserService.updateById(rsUser);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", success);
        result.put("msg", success ? "success" : "failed");
        return result;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "唯一标识", required = true)
    public Map<String, Object> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        boolean success = iRsUserService.removeById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("data", success);
        result.put("msg", success ? "success" : "failed");
        return result;
    }
}
