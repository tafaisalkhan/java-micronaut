package com.example.aop;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ResponseFilter;
import io.micronaut.http.annotation.ServerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerFilter("/**")
public class RequestLoggingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @RequestFilter
    public void logRequest(HttpRequest<?> request) {
        LOG.info("[REQUEST] {} {} from {}",
                request.getMethod(), request.getPath(), request.getRemoteAddress());
        request.setAttribute("startTime", System.currentTimeMillis());
    }

    @ResponseFilter
    public void logResponse(HttpRequest<?> request, MutableHttpResponse<?> response) {
        Long start = request.getAttribute("startTime", Long.class).orElse(0L);
        LOG.info("[RESPONSE] {} {} -> {} ({}ms)",
                request.getMethod(), request.getPath(),
                response.getStatus().getCode(),
                System.currentTimeMillis() - start);
    }
}
