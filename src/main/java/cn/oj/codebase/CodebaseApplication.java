package cn.oj.codebase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"cn.oj.codebase"})
@MapperScan(basePackages = {
        "cn.oj.codebase.generator.sys.mapper",
        "cn.oj.codebase.join.mapper",
        "cn.oj.codebase.easyrule.mapper"
})
public class CodebaseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CodebaseApplication.class, args);
    }

}

