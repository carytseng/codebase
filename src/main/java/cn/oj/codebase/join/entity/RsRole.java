package cn.oj.codebase.join.entity;

import java.util.Set;

import cn.oj.codebase.generator.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name="RsRole", description="")
public class RsRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "父级id")
    private Long pid;

    private Set<RsMenu> rsMenus;


}
