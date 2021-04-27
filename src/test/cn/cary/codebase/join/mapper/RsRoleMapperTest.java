package cn.cary.codebase.join.mapper;

import cn.cary.codebase.join.entity.RsRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-18 09:59
 **/
@Slf4j
public class RsRoleMapperTest extends BaseMapperTest {

    @Autowired
    private RsRoleMapper rsRoleMapper;

    @Test
    public void getRoleMenu() {
        List<RsRole> roles = rsRoleMapper.getRoleMenu();
        log.info(roles.toString());
    }
}