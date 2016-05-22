package servlet;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.implement.EscapeHtmlReference;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.Writer;
import java.util.Map;
import java.util.Properties;

final class TemplateUtil {

    static {
        Properties properties = new Properties();
        properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        properties.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        properties.setProperty(RuntimeConstants.EVENTHANDLER_REFERENCEINSERTION,
                EscapeHtmlReference.class.getName());
        Velocity.init(properties);
    }

    static void render(String template, Map<String, Object> data, Writer w) {
        Velocity.mergeTemplate(template, "UTF-8", new VelocityContext(data), w);
    }
}
