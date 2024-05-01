package com.youssef.gamal.library_magement_system_app.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // to be the first filter in the filter chain
public class HttpLoggingFilter extends OncePerRequestFilter {


    private ObjectMapper objectMapper;

    public HttpLoggingFilter() {
        this.objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT) // this for making the json string values in each line
                .registerModule(new JavaTimeModule()); // this for support java 8 date and time if u remove this it wil throw exceptions
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {
        CustomContentCachingRequestWrapper requestWrapper = new CustomContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        logRequest(requestWrapper);

        // process the request and response
        filterChain.doFilter(requestWrapper, responseWrapper);

        logResponse(responseWrapper);
        responseWrapper.copyBodyToResponse(); // to copy the response from the wrapper to the response
    }

    private void logRequest(CustomContentCachingRequestWrapper requestWrapper) throws IOException {
        log.info(StringUtils.repeat('=' , 100));
        String http_method = requestWrapper.getMethod();
        String url = requestWrapper.getRequestURI();
        String url_with_http_method = http_method + " " + url;
        String query_params = requestWrapper.getQueryString();
        Map body = objectMapper.readValue(requestWrapper.readRequestBodyAsString().isBlank() ? "{}" : requestWrapper.readRequestBodyAsString(), Map.class);
        Map<String, Object> headers = Collections.list(requestWrapper.getHeaderNames())
                .stream()
                .map(headerName -> Map.entry(headerName, requestWrapper.getHeader(headerName)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String,Object> request_data = new HashMap<>();
        request_data.put("url" , url_with_http_method);
        request_data.put("query_params", query_params);
        request_data.put("headers" , headers);
        request_data.put("body" , body);


        Map<String,Object> request = new HashMap<>();
        request.put("REQUEST" , request_data);

        String json_request = objectMapper.writeValueAsString(request);
        log.info(json_request);

    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {
        if(responseWrapper.getContentType() == null || !responseWrapper.getContentType().equals("application/json"))
            return;

        Map<String, Object> headers = responseWrapper.getHeaderNames()
                .stream()
                .map(headerName -> Map.entry(headerName, Objects.requireNonNull(responseWrapper.getHeader(headerName))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        String body_str = new String(responseWrapper.getContentAsByteArray());
        body_str = body_str.isBlank() ? "{}" : body_str;

        Map body = objectMapper.readValue(body_str, Map.class);

        String status_code = String.valueOf(responseWrapper.getStatus());

        Map<String,Object> response_data = new HashMap<>();
        response_data.put("headers" , headers);
        response_data.put("body" , body);
        response_data.put("status_code" , status_code);

        Map<String,Object> response = new HashMap<>();
        response.put("RESPONSE" , response_data);

        String json_response = objectMapper.writeValueAsString(response);
        log.info(json_response);

        log.info(StringUtils.repeat('=' , 100));
    }

}
