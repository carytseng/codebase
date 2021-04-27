package cn.cary.codebase.join.entity;

import cn.cary.codebase.generator.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="RsMenu对象", description="菜单表")
public class RsMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "上级菜单")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "访问路径")
    private String url;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单是否可见：0:隐藏 1可见 ")
    private Boolean visible;

    @ApiModelProperty(value = "菜单类型： 1:目录  2:菜单  3:按钮")
    private Boolean menuType;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;


}
