package cn.oj.codebase.generator.sys.entity;

import cn.oj.codebase.generator.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name="RsUser", description="用户信息表（用于测试）")
public class RsUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String actualName;

    private String password;

    @Schema(description = "性别1:男。2:女")
    private Integer gender;

    @Schema(description = "联系电话")
    private String contactNumber;

    @Schema(description = "头像地址")
    private String pictureUrl;

    @Schema(description = "邮箱地址")
    private String email;


}
