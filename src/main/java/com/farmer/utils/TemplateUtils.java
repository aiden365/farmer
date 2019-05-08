package com.farmer.utils;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author farmer
 * @version 1.0
 * @data 2019/4/26 13:18
 **/
public class TemplateUtils {

    public static void exportFreemarkerTemplate(String templateName, ByteArrayOutputStream bos, Map<String, Object> params) throws FileNotFoundException {

        TemplateConfig templateConfig = new TemplateConfig(ConstKit.TEMPLATE_PATH, TemplateConfig.ResourceMode.CLASSPATH);

        templateConfig.setCharset(Charset.forName("utf-8"));

        FreemarkerEngine freemarkerEngine = new FreemarkerEngine(templateConfig);

        Template template = freemarkerEngine.getTemplate(templateName);
        if(bos == null){
            throw new FileNotFoundException("目标流不存在");
        }

        //渲染并输出模板
        template.render(params,bos);
    }

}
