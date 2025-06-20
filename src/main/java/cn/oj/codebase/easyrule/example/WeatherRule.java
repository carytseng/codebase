package cn.oj.codebase.easyrule.example;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName WeatherRule.java
 * @Description TODO
 * @createTime 2021年12月21日 16:34:00
 */
@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule {

    @Condition
    public boolean itRains(@Fact("rain") boolean rain) {
        return rain;
    }

    @Action
    public void takeAnUmbrella() {
        System.out.println("It rains, take an umbrella!");
    }
}