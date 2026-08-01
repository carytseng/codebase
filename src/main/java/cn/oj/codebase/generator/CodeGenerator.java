package cn.oj.codebase.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @program: codebase
 * @description: MyBatis-Plus 3.5.x 代码生成器
 *               运行前请修改数据库连接信息及表名
 * @author: 郑剑锋
 * @create: 2021-04-10 20:32
 **/
public class CodeGenerator {

    /**
     * 数据库连接配置 — 请修改为实际数据库信息
     */
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/data_collection?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf-8";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345678";

    /**
     * 包名配置
     */
    private static final String BASE_PACKAGE = "cn.oj.codebase.generator";
    private static final String ENTITY_PACKAGE = BASE_PACKAGE + ".sys.entity";
    private static final String MAPPER_PACKAGE = BASE_PACKAGE + ".sys.mapper";
    private static final String SERVICE_PACKAGE = BASE_PACKAGE + ".sys.service";
    private static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".sys.controller";

    /**
     * 作者
     */
    private static final String AUTHOR = "郑剑锋";

    /**
     * 要生成的表名（可多个，用逗号分隔）
     */
    private static final String TABLE_NAMES = "rs_user,rs_menu,rs_role,rs_role_menu";

    public static void main(String[] args) {
        // 项目根路径
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHOR)
                            .commentDate("yyyy-MM-dd")
                            .outputDir(projectPath + "/src/main/java")
                            .disableOpenDir()
                            .enableSwagger()
                            .build();
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(BASE_PACKAGE)
                            .entity("sys.entity")
                            .mapper("sys.mapper")
                            .service("sys.service")
                            .serviceImpl("sys.service.impl")
                            .controller("sys.controller")
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.xml,
                                    projectPath + "/src/main/resources/mapper/sys"
                            ))
                            .build();
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAMES)
                            .addTablePrefix("rs_")  // 过滤表前缀

                            // Entity 策略
                            .entityBuilder()
                            .superClass("cn.oj.codebase.generator.base.BaseEntity")
                            .addSuperEntityColumns("id", "create_by", "create_time", "update_by", "update_time", "del_flag")
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .formatFileName("%s")
                            .build()

                            // Mapper 策略
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")
                            .build()

                            // Service 策略
                            .serviceBuilder()
                            .formatServiceFileName("I%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .build()

                            // Controller 策略
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .formatFileName("%sController")
                            .build();
                })
                // 模板引擎（Freemarker）
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
