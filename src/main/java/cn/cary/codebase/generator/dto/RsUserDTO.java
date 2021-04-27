package cn.cary.codebase.generator.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * @program: codebase
 * @description: 用户信息DTO
 * @author: 郑剑锋
 * @create: 2021-04-11 11:14
 **/
@Data
public class RsUserDTO {

    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名不能超过20个字符")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "真实姓名不能为空")
    @Length(max = 20, message = "真实姓名不能超过20个字符")
    @ApiModelProperty(value = "真实姓名")
    private String actualName;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull
    @ApiModelProperty(value = "性别1:男。2:女")
    private Integer gender;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "头像地址")
    private String pictureUrl;

    @NotBlank(message = "联系邮箱不能为空")
    @ApiModelProperty(value = "邮箱地址")
    private String email;
}
