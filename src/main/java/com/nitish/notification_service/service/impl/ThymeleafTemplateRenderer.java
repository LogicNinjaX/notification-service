package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.util.TemplateUtil;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Component
public class ThymeleafTemplateRenderer {

    private final SpringTemplateEngine templateEngine;
    private final TemplateUtil templateUtil;

    public ThymeleafTemplateRenderer(SpringTemplateEngine templateEngine, TemplateUtil templateUtil) {
        this.templateEngine = templateEngine;
        this.templateUtil = templateUtil;
    }

    public String render(String template, Map<String, Object> variables){
        String thymeleafTemplate =
                templateUtil.convertToThymeleafSyntax(template);

        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process(thymeleafTemplate, context);
    }
}
