package cn.cary.codebase.generator.sys.service.impl;

import cn.cary.codebase.generator.sys.entity.RsUser;
import cn.cary.codebase.generator.sys.mapper.RsUserMapper;
import cn.cary.codebase.generator.sys.service.IRsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表（用于测试） 服务实现类
 * </p>
 *
 * @author 郑剑锋
 * @since 2021-04-11
 */
@Service
public class RsUserServiceImpl extends ServiceImpl<RsUserMapper, RsUser> implements IRsUserService {

}
