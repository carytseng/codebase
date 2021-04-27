package cn.cary.codebase.generator.sys.controller;


import cn.cary.codebase.generator.dto.RsUserDTO;
import cn.cary.codebase.generator.sys.entity.RsUser;
import cn.cary.codebase.generator.sys.service.IRsUserService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.api.ApiController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


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
@Api(tags = {"用户信息"})
public class RsUserController extends ApiController {

    @Autowired
    private IRsUserService iRsUserService;

    @GetMapping("/{id}")
    @ApiOperation(value = "查询")
    @ApiImplicitParam(name = "id", value = "唯一标识", required = true, paramType = "path", dataType = "Long")
    public R<RsUser> get(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        return R.ok(iRsUserService.getById(id));
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增")
    public R<Boolean> insert(@Valid RsUserDTO rsUserDTO) {
        RsUser rsUser = new RsUser();
        BeanUtils.copyProperties(rsUserDTO, rsUser);
        return R.ok(iRsUserService.save(rsUser));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新")
    public R<Boolean> update(@Valid RsUserDTO rsUserDTO) {
        RsUser rsUser = new RsUser();
        BeanUtils.copyProperties(rsUserDTO, rsUser);
        return R.ok(iRsUserService.updateById(rsUser));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "唯一标识", required = true, paramType = "path", dataType = "Long")
    public R<Boolean> delete(@PathVariable @NotNull(message = "id不能为空") Long id) {
        return R.ok(iRsUserService.removeById(id));
    }
}
