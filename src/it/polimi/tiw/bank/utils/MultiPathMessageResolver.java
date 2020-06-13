package it.polimi.tiw.bank.utils;

import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.ServletContextTemplateResource;

import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.Map;

public class MultiPathMessageResolver extends StandardMessageResolver {

    private final ServletContext context;
    private final String directory;

    public MultiPathMessageResolver(ServletContext context, String path) {
        super();
        this.context = context;
        this.directory = path;
    }

    @Override
    protected Map<String, String> resolveMessagesForTemplate(String template, ITemplateResource templateResource, Locale locale) {
        // System.out.println(template);
        String regex = "(.*)(/[^/]*$)";
        String file = template.replaceFirst(regex, "$2");
        // System.out.println(file);
        String finalpath = "/WEB-INF/" + directory + file;
        // System.out.println(finalpath);
        templateResource = new ServletContextTemplateResource(context, finalpath, null);
        return super.resolveMessagesForTemplate(finalpath, templateResource, locale);
    }

}
