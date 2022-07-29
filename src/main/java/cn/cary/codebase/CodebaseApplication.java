package cn.cary.codebase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = {"cn.cary.codebase.**.mapper"})
@SpringBootApplication(scanBasePackages = {"cn.cary.codebase"}, exclude = {DataSourceAutoConfiguration.class})
public class CodebaseApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CodebaseApplication.class, args);

    }

}


