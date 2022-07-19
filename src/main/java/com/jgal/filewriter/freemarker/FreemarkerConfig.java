package com.jgal.filewriter.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
 
public enum FreemarkerConfig {
 
    INSTANCE;
    private Configuration cfg;
 
    private FreemarkerConfig() {
        ClassTemplateLoader loader = new ClassTemplateLoader(this.getClass(),"/GUItemplates");
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }
 
    public static Configuration getConfig() {
        return INSTANCE.cfg;
    }
}
