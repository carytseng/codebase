package cn.oj.codebase.easyrule.example;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName RuleDemo.java
 * @Description TODO
 * @createTime 2021年12月21日 16:33:00
 */
public class RuleDemo {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Facts facts = new Facts();
        facts.put("rain", false);

        // define rules
        Rule weatherRule = new RuleBuilder()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when(v -> v.get("rain").equals(true))
                .then(v -> System.out.println("It rains, take an umbrella!"))
                .build();
        Rules rules = new Rules();
        rules.register(weatherRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);
    }
}
