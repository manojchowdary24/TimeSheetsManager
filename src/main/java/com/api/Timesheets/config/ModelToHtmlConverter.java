package com.api.Timesheets.config;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ModelToHtmlConverter {

  public static final String RESET_PASSWORD_USER_HTML_TEMPLATE = "reset_password_email_template.ftl";

  public static final String REQUEST_ACCESS_USER_HTML_TEMPLATE = "access_request_email_template.ftl";

  @Autowired
  private Configuration freemarkerConfiguration;

  public String convert(String templateName, Map<String, Object> model)
      throws IOException, TemplateException {
    Template template = freemarkerConfiguration.getTemplate(templateName, "UTF-8");

    // Configure template to support map?api.get()
    template.setAPIBuiltinEnabled(true);
    ((DefaultObjectWrapper) template.getObjectWrapper()).setUseAdaptersForContainers(true);

    return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
  }
}
