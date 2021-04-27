package cn.cary.codebase.join.entity;

import cn.cary.codebase.generator.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="RsRoleMenu对象", description="角色菜单关联表")
public class RsRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


}
