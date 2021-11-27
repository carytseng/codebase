package cn.cary.codebase.npe;

import cn.cary.codebase.generator.sys.entity.RsUser;

import java.util.Optional;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName EntityCheck.java
 * @Description TODO
 * @createTime 2021年11月16日 16:16:00
 */
public class EntityCheck {
    public static void main(String[] args) {
        RsUser user = null;
        String actualName = Optional.ofNullable(user).map(u -> u.getActualName()).orElseGet(()->{return null;});
        System.out.println(actualName);
    }
}
