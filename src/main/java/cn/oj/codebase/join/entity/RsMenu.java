package cn.oj.codebase.join.entity;

import cn.oj.codebase.generator.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name="RsMenu", description="菜单表")
public class RsMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "上级菜单")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "访问路径")
    private String url;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单是否可见：0:隐藏 1可见 ")
    private Boolean visible;

    @Schema(description = "菜单类型： 1:目录  2:菜单  3:按钮")
    private Boolean menuType;

    @Schema(description = "排序")
    private Integer orderNum;


}
