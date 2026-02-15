package com.content.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.content.common.generator.config.CodeGeneratorProperties;
import com.content.common.generator.util.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CodeGenerator {

    @Autowired
    private CodeGeneratorProperties properties;

    public void generateCode() {
        generateCode(properties);
    }

    public void generateCode(CodeGeneratorProperties props) {
        log.info("开始生成代码...");
        log.info("数据库配置: {}", props.getDatabase());
        log.info("包配置: {}", props.getPackageConfig());
        log.info("全局配置: {}", props.getGlobalConfig());
        log.info("策略配置: {}", props.getStrategyConfig());
        log.info("模板配置: {}", props.getTemplateConfig());

        String url = props.getDatabase().getUrl();
        String username = props.getDatabase().getUsername();
        String password = props.getDatabase().getPassword();

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(props.getGlobalConfig().getAuthor())
                            .outputDir(props.getGlobalConfig().getOutputDir())
                            .commentDate(props.getGlobalConfig().getCommentDate())
                            .dateType(GeneratorUtils.getDateType(props.getGlobalConfig().getDateType()))
                            .enableSwagger();
                    if (props.getGlobalConfig().isDisableOpenDir()) {
                        builder.disableOpenDir();
                    }
                })
                .packageConfig(builder -> {
                    Map<OutputFile, String> pathInfo = new HashMap<>();
                    String xmlOutputDir = GeneratorUtils.getOutputDir(
                            props.getGlobalConfig().getXmlOutputDir(),
                            props.getPackageConfig().getXml()
                    );
                    pathInfo.put(OutputFile.xml, xmlOutputDir);
                    
                    builder.parent(props.getPackageConfig().getParent())
                            .moduleName(props.getPackageConfig().getModuleName())
                            .entity(props.getPackageConfig().getEntity())
                            .mapper(props.getPackageConfig().getMapper())
                            .service(props.getPackageConfig().getService())
                            .serviceImpl(props.getPackageConfig().getServiceImpl())
                            .controller(props.getPackageConfig().getController())
                            .pathInfo(pathInfo);
                })
                .strategyConfig(builder -> {
                    builder.addInclude(GeneratorUtils.listToArray(props.getStrategyConfig().getInclude()))
                            .addExclude(GeneratorUtils.listToArray(props.getStrategyConfig().getExclude()))
                            .addTablePrefix(GeneratorUtils.listToArray(props.getStrategyConfig().getTablePrefix()))
                            .addFieldPrefix(GeneratorUtils.listToArray(props.getStrategyConfig().getFieldPrefix()))
                            .entityBuilder()
                            .enableLombok()
                            .naming(GeneratorUtils.getNamingStrategy(props.getStrategyConfig().getNaming()))
                            .columnNaming(GeneratorUtils.getNamingStrategy(props.getStrategyConfig().getColumnNaming()))
                            .superClass(GeneratorUtils.getDefaultIfEmpty(props.getStrategyConfig().getSuperEntityClass(), ""))
                            .addSuperEntityColumns(GeneratorUtils.listToArray(props.getStrategyConfig().getSuperEntityColumns()))
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .superClass(GeneratorUtils.getDefaultIfEmpty(props.getStrategyConfig().getSuperMapperClass(), ""))
                            .serviceBuilder()
                            .superServiceClass(GeneratorUtils.getDefaultIfEmpty(props.getStrategyConfig().getSuperServiceClass(), ""))
                            .superServiceImplClass(GeneratorUtils.getDefaultIfEmpty(props.getStrategyConfig().getSuperServiceImplClass(), ""))
                            .formatServiceFileName(props.getStrategyConfig().getFormatServiceFileName())
                            .formatServiceImplFileName(props.getStrategyConfig().getFormatServiceImplFileName())
                            .controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle()
                            .superClass(GeneratorUtils.getDefaultIfEmpty(props.getStrategyConfig().getSuperControllerClass(), ""))
                            .formatFileName(props.getStrategyConfig().getFormatControllerFileName());
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        log.info("代码生成完成!");
    }

    public static void main(String[] args) {
        CodeGeneratorProperties props = GeneratorUtils.getDefaultProperties();
        new CodeGenerator().generateCode(props);
    }
}
