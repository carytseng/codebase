package cn.cary.codebase.generator.sys.entity;

import cn.cary.codebase.generator.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息表（用于测试）
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="RsUser对象", description="用户信息表（用于测试）")
public class RsUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String actualName;

    private String password;

    @ApiModelProperty(value = "性别1:男。2:女")
    private Integer gender;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "头像地址")
    private String pictureUrl;

    @ApiModelProperty(value = "邮箱地址")
    private String email;


}
