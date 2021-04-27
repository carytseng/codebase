package cn.cary.codebase.join.mapper;

import cn.cary.codebase.join.entity.RsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-18
 */
public interface RsRoleMapper extends BaseMapper<RsRole> {
    List<RsRole> getRoleMenu();
}
