package utils;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Custom Log4j2 appender that collects log messages for ExtentReports.
 */
@Plugin(name = "ExtentLogAppender", category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ExtentLogAppender extends AbstractAppender {

    private static final ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

    protected ExtentLogAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout, false, null);
    }

    @Override
    public void append(LogEvent event) {
        String message = getLayout().toSerializable(event).toString();
        logQueue.add(message);
    }

    public static String getLogs() {
        StringBuilder sb = new StringBuilder();
        for (String log : logQueue) {
            sb.append(log).append("\n");
        }
        return sb.toString();
    }

    public static void clearLogs() {
        logQueue.clear();
    }

    @PluginFactory
    public static ExtentLogAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") Filter filter) {

        if (name == null) {
            LOGGER.error("No name provided for ExtentLogAppender");
            return null;
        }

        if (layout == null) {
            layout = org.apache.logging.log4j.core.layout.PatternLayout.createDefaultLayout();
        }

        return new ExtentLogAppender(name, filter, layout);
    }
}
