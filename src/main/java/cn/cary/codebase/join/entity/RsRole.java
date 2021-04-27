package cn.cary.codebase.join.entity;

import java.time.LocalDateTime;
import java.util.Set;

import cn.cary.codebase.generator.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="RsRole对象", description="")
public class RsRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "父级id")
    private Long pid;

    private Set<RsMenu> rsMenus;


}
