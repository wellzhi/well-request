package com.liuxz.request.util;

import cn.hutool.core.bean.BeanUtil;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import com.liuxz.request.model.ApiDocModel;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

public class ApiUtil {
    public static void genApiDoc(ApiDocModel docModel) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();

            String apiDocTemplate = UrlUtil.loadText(ApiUtil.class.getResource("/template/ApiDoc.ftl"));

            stringTemplateLoader.putTemplate("ApiDocTemplate", apiDocTemplate);
            configuration.setTemplateLoader(stringTemplateLoader);
            Template template = configuration.getTemplate("ApiDocTemplate");
            Map<String, Object> contentMap = BeanUtil.beanToMap(docModel);
            File file = new File(docModel.getApiFilePath());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            template.process(contentMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
