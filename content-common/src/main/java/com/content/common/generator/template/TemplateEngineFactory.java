package com.content.common.generator.template;

import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class TemplateEngineFactory {

    public static AbstractTemplateEngine create(String type) {
        return switch (type.toLowerCase()) {
            case "velocity" -> new VelocityTemplateEngine();
            case "freemarker" -> new FreemarkerTemplateEngine();
            default -> new FreemarkerTemplateEngine();
        };
    }

    public static AbstractTemplateEngine createFreemarker() {
        return new FreemarkerTemplateEngine();
    }

    public static AbstractTemplateEngine createVelocity() {
        return new VelocityTemplateEngine();
    }
}
