package edu.ksu.canvas.errors;

import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;

/**
 * This allows additional specific behaviour for handling errors.
 */
public interface ErrorHandler {

    boolean shouldHandle(ClassicHttpRequest httpRequest, ClassicHttpResponse httpResponse);

    void handle(ClassicHttpRequest httpRequest, ClassicHttpResponse httpResponse) throws ParseException;
}
