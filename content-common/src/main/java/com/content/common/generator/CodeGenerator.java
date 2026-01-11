package com.content.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * MyBatis Plus 代码生成器
 * 用于根据数据库表自动生成实体类、Mapper、Service和Controller
 */
public class CodeGenerator {

    /**
     * 数据库配置
     */
    private static final String URL = "jdbc:mysql://localhost:3306/novel_sharing?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    /**
     * 包配置
     */
    private static final String PARENT_PACKAGE = "com.content";
    private static final String MODULE_NAME = "user";
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/java";
    private static final String MAPPER_OUTPUT_DIR = System.getProperty("user.dir") + "/src/main/resources/mapper";

    /**
     * 作者信息
     */
    private static final String AUTHOR = "content";

    /**
     * 表配置
     */
    private static final String[] TABLES = {"user"};
    private static final String[] TABLE_PREFIX = {"t_"};

    /**
     * 主方法
     */
    public static void main(String[] args) {
        generateCode();
    }

    /**
     * 生成代码
     */
    public static void generateCode() {
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author(AUTHOR)
                            .outputDir(OUTPUT_DIR)
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .enableSwagger()
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent(PARENT_PACKAGE)
                            .moduleName(MODULE_NAME)
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_OUTPUT_DIR));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES)
                            .addTablePrefix(TABLE_PREFIX)
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .controllerBuilder()
                            .enableRestStyle()
                            .formatFileName("%sController");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 自定义生成代码方法
     * @param url 数据库URL
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param parentPackage 父包名
     * @param moduleName 模块名
     * @param tables 表名
     * @param tablePrefix 表前缀
     */
    public static void generateCode(String url, String username, String password,
                                    String parentPackage, String moduleName, String[] tables, String[] tablePrefix) {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(AUTHOR)
                            .outputDir(OUTPUT_DIR)
                            .commentDate("yyyy-MM-dd")
                            .dateType(DateType.ONLY_DATE)
                            .enableSwagger()
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent(parentPackage)
                            .moduleName(moduleName)
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_OUTPUT_DIR));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix(tablePrefix)
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .controllerBuilder()
                            .enableRestStyle()
                            .formatFileName("%sController");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}