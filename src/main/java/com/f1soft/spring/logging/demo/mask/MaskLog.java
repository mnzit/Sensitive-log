package com.f1soft.spring.logging.demo.mask;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Use PatternLayout in logback -> https://medium.com/@kolapkar.dhaval/mask-sensitive-data-in-logs-7e06496e56c1

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com reference:
 * https://gomtiprabha.wordpress.com/2014/04/04/masking-sensitive-info-or-data-in-logs/
 */
@Plugin(name = "MessagePatternConverter", category = "Converter")
@ConverterKeys({"xmsg", "bankxp"})
public class MaskLog extends LogEventPatternConverter {

    public MaskLog(String[] options) {
        super("bankxp", "bankxp");
    }

    public static MaskLog newInstance(final String[] options) {
        return new MaskLog(options);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder outputMsg) {
        String message = logEvent.getMessage().getFormat();
        String REGEX_CREDIT_CARD = "([0-9]{6})[0-9]{0,9}([0-9]{4})";

        String JSON_KEYS = Arrays.asList("cardNo", "password").stream().collect(Collectors.joining("|"));

        String JSON_REGEX = Pattern.compile("\"*(" + JSON_KEYS + ")\"*(:|=)(\"*(\\\\\"|[^\"])*\"*|\\[(\"(\\\\\"|[^\"])*\"(,\"(\\\\\"|[^\"])*\")*)?\\])").pattern();

        message = replaceMatched(message,"\"************\"");
        outputMsg.append(message);
    }
    private static final String JSON_KEYS = Arrays.asList("cardNo", "password").stream().collect(Collectors.joining("|"));
    private static final Pattern PATTERN = Pattern.compile("\"*(" + JSON_KEYS + ")\"*(:|=)(\"*(\\\\\"|[^\"])*\"*|\\[(\"(\\\\\"|[^\"])*\"(,\"(\\\\\"|[^\"])*\")*)?\\])", Pattern.CASE_INSENSITIVE);
    private static String replaceMatched(String input, String replacement) {
        Matcher m = PATTERN.matcher(input);

        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            Matcher m2 = PATTERN.matcher(m.group(0));
            if (m2.find()) {
                StringBuilder stringBuilder = new StringBuilder(m2.group(0));
                String result = stringBuilder.replace(m2.start(3), m2.end(3), replacement).toString();
                m.appendReplacement(sb, result);
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
