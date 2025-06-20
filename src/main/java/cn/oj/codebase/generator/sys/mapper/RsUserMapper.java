package cn.oj.codebase.generator.sys.mapper;

import cn.oj.codebase.generator.sys.entity.RsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户信息表（用于测试） Mapper 接口
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-11
 */
@Mapper
@Component
public interface RsUserMapper extends BaseMapper<RsUser> {

}
