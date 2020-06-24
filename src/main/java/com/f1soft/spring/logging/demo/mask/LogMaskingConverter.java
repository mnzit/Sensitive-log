package com.f1soft.spring.logging.demo.mask;

import com.f1soft.spring.logging.demo.marker.LoggingMarkers;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// /\"*(phoneNo|password|email)"*(:|=)("*(\\"|[^"])*"*|\[("(\\"|[^"])*"(,"(\\"|[^"])*")*)?\])

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Plugin(name = "LogMaskingConverter", category = "Converter")
@ConverterKeys({"xmsg", "bankxp"})
public class LogMaskingConverter extends LogEventPatternConverter {
    private static final String JSON_REPLACEMENT_REGEX = "\"\\$1\":  \"****\"";
    private static final String JSON_KEYS = Arrays.asList("ssn", "private", "creditcard").stream().collect(Collectors.joining("|"));
    private static final Pattern JSON_PATTERN = Pattern.compile("/\\\"*(" + JSON_KEYS + ")\"*(:|=)(\"*(\\\\\"|[^\"])*\"*|\\[(\"(\\\\\"|[^\"])*\"(,\"(\\\\\"|[^\"])*\")*)?\\])");

    public LogMaskingConverter(String[] options) {
        super("bankxp", "bankxp");
    }

    public static LogMaskingConverter newInstance(final String[] options) {
        return new LogMaskingConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;

        if (event.getMarker().getName() == LoggingMarkers.JSON.getName()) {
            try {
                maskedMessage = mask(message);

            } catch (Exception e) {
                maskedMessage = message; // Although if this fails, it may be better to not log the message
            }
        }

        outputMessage.append(maskedMessage);
    }

    private String mask(String message) {
        StringBuffer buffer = new StringBuffer();
        Matcher matcher = JSON_PATTERN.matcher(message);

        while (matcher.find()) {
            matcher.appendReplacement(buffer, JSON_REPLACEMENT_REGEX);
        }

        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
