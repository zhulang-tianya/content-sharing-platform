package com.content.common.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "code.generator")
public class CodeGeneratorProperties {

    private Database database = new Database();
    
    private PackageConfig packageConfig = new PackageConfig();
    
    private GlobalConfig globalConfig = new GlobalConfig();
    
    private StrategyConfig strategyConfig = new StrategyConfig();
    
    private TemplateConfig templateConfig = new TemplateConfig();
    
    public Database getDatabase() {
        return database;
    }
    
    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public PackageConfig getPackageConfig() {
        return packageConfig;
    }
    
    public void setPackageConfig(PackageConfig packageConfig) {
        this.packageConfig = packageConfig;
    }
    
    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }
    
    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }
    
    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }
    
    public void setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }
    
    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }
    
    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }
    
    public static class Database {
        private String url = "jdbc:mysql://localhost:3306/novel_sharing?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
        private String username = "root";
        private String password = "password";
        private String driverClassName = "com.mysql.cj.jdbc.Driver";
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
        
        public String getDriverClassName() {
            return driverClassName;
        }
        
        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
    
    public static class PackageConfig {
        private String parent = "com.content";
        private String moduleName = "";
        private String entity = "entity";
        private String mapper = "mapper";
        private String service = "service";
        private String serviceImpl = "service.impl";
        private String controller = "controller";
        private String xml = "mapper";
        
        public String getParent() {
            return parent;
        }
        
        public void setParent(String parent) {
            this.parent = parent;
        }
        
        public String getModuleName() {
            return moduleName;
        }
        
        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }
        
        public String getEntity() {
            return entity;
        }
        
        public void setEntity(String entity) {
            this.entity = entity;
        }
        
        public String getMapper() {
            return mapper;
        }
        
        public void setMapper(String mapper) {
            this.mapper = mapper;
        }
        
        public String getService() {
            return service;
        }
        
        public void setService(String service) {
            this.service = service;
        }
        
        public String getServiceImpl() {
            return serviceImpl;
        }
        
        public void setServiceImpl(String serviceImpl) {
            this.serviceImpl = serviceImpl;
        }
        
        public String getController() {
            return controller;
        }
        
        public void setController(String controller) {
            this.controller = controller;
        }
        
        public String getXml() {
            return xml;
        }
        
        public void setXml(String xml) {
            this.xml = xml;
        }
    }
    
    public static class GlobalConfig {
        private String author = "content";
        private String outputDir = System.getProperty("user.dir") + "/src/main/java";
        private String xmlOutputDir = System.getProperty("user.dir") + "/src/main/resources";
        private String commentDate = "yyyy-MM-dd";
        private boolean enableSwagger = true;
        private boolean disableOpenDir = true;
        private String dateType = "ONLY_DATE";
        
        public String getAuthor() {
            return author;
        }
        
        public void setAuthor(String author) {
            this.author = author;
        }
        
        public String getOutputDir() {
            return outputDir;
        }
        
        public void setOutputDir(String outputDir) {
            this.outputDir = outputDir;
        }
        
        public String getXmlOutputDir() {
            return xmlOutputDir;
        }
        
        public void setXmlOutputDir(String xmlOutputDir) {
            this.xmlOutputDir = xmlOutputDir;
        }
        
        public String getCommentDate() {
            return commentDate;
        }
        
        public void setCommentDate(String commentDate) {
            this.commentDate = commentDate;
        }
        
        public boolean isEnableSwagger() {
            return enableSwagger;
        }
        
        public void setEnableSwagger(boolean enableSwagger) {
            this.enableSwagger = enableSwagger;
        }
        
        public boolean isDisableOpenDir() {
            return disableOpenDir;
        }
        
        public void setDisableOpenDir(boolean disableOpenDir) {
            this.disableOpenDir = disableOpenDir;
        }
        
        public String getDateType() {
            return dateType;
        }
        
        public void setDateType(String dateType) {
            this.dateType = dateType;
        }
    }
    
    public static class StrategyConfig {
        private List<String> include = List.of("user");
        private List<String> exclude = List.of();
        private List<String> tablePrefix = List.of("t_");
        private List<String> fieldPrefix = List.of();
        private boolean enableLombok = true;
        private boolean enableTableFieldAnnotation = true;
        private boolean enableChainModel = true;
        private boolean enableSerialVersionUID = true;
        private boolean enableColumnConstant = false;
        private boolean enableActiveRecord = false;
        private String naming = "underline_to_camel";
        private String columnNaming = "underline_to_camel";
        private boolean enableBaseResultMap = true;
        private boolean enableBaseColumnList = true;
        private boolean enableFileOverride = false;
        private String formatServiceFileName = "%sService";
        private String formatServiceImplFileName = "%sServiceImpl";
        private String formatMapperFileName = "%sMapper";
        private String formatXmlFileName = "%sMapper";
        private String formatControllerFileName = "%sController";
        private boolean enableRestStyle = true;
        private boolean enableHyphenStyle = false;
        private String superEntityClass = "";
        private List<String> superEntityColumns = List.of();
        private String superMapperClass = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
        private String superServiceClass = "com.baomidou.mybatisplus.extension.service.IService";
        private String superServiceImplClass = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
        private String superControllerClass = "";
        
        public List<String> getInclude() {
            return include;
        }
        
        public void setInclude(List<String> include) {
            this.include = include;
        }
        
        public List<String> getExclude() {
            return exclude;
        }
        
        public void setExclude(List<String> exclude) {
            this.exclude = exclude;
        }
        
        public List<String> getTablePrefix() {
            return tablePrefix;
        }
        
        public void setTablePrefix(List<String> tablePrefix) {
            this.tablePrefix = tablePrefix;
        }
        
        public List<String> getFieldPrefix() {
            return fieldPrefix;
        }
        
        public void setFieldPrefix(List<String> fieldPrefix) {
            this.fieldPrefix = fieldPrefix;
        }
        
        public boolean isEnableLombok() {
            return enableLombok;
        }
        
        public void setEnableLombok(boolean enableLombok) {
            this.enableLombok = enableLombok;
        }
        
        public boolean isEnableTableFieldAnnotation() {
            return enableTableFieldAnnotation;
        }
        
        public void setEnableTableFieldAnnotation(boolean enableTableFieldAnnotation) {
            this.enableTableFieldAnnotation = enableTableFieldAnnotation;
        }
        
        public boolean isEnableChainModel() {
            return enableChainModel;
        }
        
        public void setEnableChainModel(boolean enableChainModel) {
            this.enableChainModel = enableChainModel;
        }
        
        public boolean isEnableSerialVersionUID() {
            return enableSerialVersionUID;
        }
        
        public void setEnableSerialVersionUID(boolean enableSerialVersionUID) {
            this.enableSerialVersionUID = enableSerialVersionUID;
        }
        
        public boolean isEnableColumnConstant() {
            return enableColumnConstant;
        }
        
        public void setEnableColumnConstant(boolean enableColumnConstant) {
            this.enableColumnConstant = enableColumnConstant;
        }
        
        public boolean isEnableActiveRecord() {
            return enableActiveRecord;
        }
        
        public void setEnableActiveRecord(boolean enableActiveRecord) {
            this.enableActiveRecord = enableActiveRecord;
        }
        
        public String getNaming() {
            return naming;
        }
        
        public void setNaming(String naming) {
            this.naming = naming;
        }
        
        public String getColumnNaming() {
            return columnNaming;
        }
        
        public void setColumnNaming(String columnNaming) {
            this.columnNaming = columnNaming;
        }
        
        public boolean isEnableBaseResultMap() {
            return enableBaseResultMap;
        }
        
        public void setEnableBaseResultMap(boolean enableBaseResultMap) {
            this.enableBaseResultMap = enableBaseResultMap;
        }
        
        public boolean isEnableBaseColumnList() {
            return enableBaseColumnList;
        }
        
        public void setEnableBaseColumnList(boolean enableBaseColumnList) {
            this.enableBaseColumnList = enableBaseColumnList;
        }
        
        public boolean isEnableFileOverride() {
            return enableFileOverride;
        }
        
        public void setEnableFileOverride(boolean enableFileOverride) {
            this.enableFileOverride = enableFileOverride;
        }
        
        public String getFormatServiceFileName() {
            return formatServiceFileName;
        }
        
        public void setFormatServiceFileName(String formatServiceFileName) {
            this.formatServiceFileName = formatServiceFileName;
        }
        
        public String getFormatServiceImplFileName() {
            return formatServiceImplFileName;
        }
        
        public void setFormatServiceImplFileName(String formatServiceImplFileName) {
            this.formatServiceImplFileName = formatServiceImplFileName;
        }
        
        public String getFormatMapperFileName() {
            return formatMapperFileName;
        }
        
        public void setFormatMapperFileName(String formatMapperFileName) {
            this.formatMapperFileName = formatMapperFileName;
        }
        
        public String getFormatXmlFileName() {
            return formatXmlFileName;
        }
        
        public void setFormatXmlFileName(String formatXmlFileName) {
            this.formatXmlFileName = formatXmlFileName;
        }
        
        public String getFormatControllerFileName() {
            return formatControllerFileName;
        }
        
        public void setFormatControllerFileName(String formatControllerFileName) {
            this.formatControllerFileName = formatControllerFileName;
        }
        
        public boolean isEnableRestStyle() {
            return enableRestStyle;
        }
        
        public void setEnableRestStyle(boolean enableRestStyle) {
            this.enableRestStyle = enableRestStyle;
        }
        
        public boolean isEnableHyphenStyle() {
            return enableHyphenStyle;
        }
        
        public void setEnableHyphenStyle(boolean enableHyphenStyle) {
            this.enableHyphenStyle = enableHyphenStyle;
        }
        
        public String getSuperEntityClass() {
            return superEntityClass;
        }
        
        public void setSuperEntityClass(String superEntityClass) {
            this.superEntityClass = superEntityClass;
        }
        
        public List<String> getSuperEntityColumns() {
            return superEntityColumns;
        }
        
        public void setSuperEntityColumns(List<String> superEntityColumns) {
            this.superEntityColumns = superEntityColumns;
        }
        
        public String getSuperMapperClass() {
            return superMapperClass;
        }
        
        public void setSuperMapperClass(String superMapperClass) {
            this.superMapperClass = superMapperClass;
        }
        
        public String getSuperServiceClass() {
            return superServiceClass;
        }
        
        public void setSuperServiceClass(String superServiceClass) {
            this.superServiceClass = superServiceClass;
        }
        
        public String getSuperServiceImplClass() {
            return superServiceImplClass;
        }
        
        public void setSuperServiceImplClass(String superServiceImplClass) {
            this.superServiceImplClass = superServiceImplClass;
        }
        
        public String getSuperControllerClass() {
            return superControllerClass;
        }
        
        public void setSuperControllerClass(String superControllerClass) {
            this.superControllerClass = superControllerClass;
        }
    }
    
    public static class TemplateConfig {
        private String entity = "";
        private String mapper = "";
        private String service = "";
        private String serviceImpl = "";
        private String controller = "";
        private String xml = "";
        
        public String getEntity() {
            return entity;
        }
        
        public void setEntity(String entity) {
            this.entity = entity;
        }
        
        public String getMapper() {
            return mapper;
        }
        
        public void setMapper(String mapper) {
            this.mapper = mapper;
        }
        
        public String getService() {
            return service;
        }
        
        public void setService(String service) {
            this.service = service;
        }
        
        public String getServiceImpl() {
            return serviceImpl;
        }
        
        public void setServiceImpl(String serviceImpl) {
            this.serviceImpl = serviceImpl;
        }
        
        public String getController() {
            return controller;
        }
        
        public void setController(String controller) {
            this.controller = controller;
        }
        
        public String getXml() {
            return xml;
        }
        
        public void setXml(String xml) {
            this.xml = xml;
        }
    }
}
