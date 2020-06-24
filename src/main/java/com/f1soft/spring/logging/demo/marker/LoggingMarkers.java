package com.f1soft.spring.logging.demo.marker;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
public class LoggingMarkers {
    public static final Marker JSON = MarkerFactory.getMarker("JSON-MASK");
    public static final Marker XML = MarkerFactory.getMarker("XML-MASK");
}