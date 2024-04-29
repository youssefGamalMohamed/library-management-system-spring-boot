package com.youssef.gamal.library_magement_system_app.logging;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
/*
* this class created to read request body as string from the request in safe way before process it in the filter
* to avoid NULL exceptions and the important method is ==> "String readRequestBodyAsString()"
* */
public class CustomContentCachingRequestWrapper extends ContentCachingRequestWrapper {

    private static class SimpleServletInputStream extends ServletInputStream {
        private InputStream delegate;

        public SimpleServletInputStream(byte[] data) {
            this.delegate = new ByteArrayInputStream(data);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return this.delegate.read();
        }
    }

    private SimpleServletInputStream inputStream;

    public CustomContentCachingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.inputStream;
    }

    public String readRequestBodyAsString() throws IOException {
        if (inputStream == null) {
            byte[] body = super.getInputStream().readAllBytes();
            this.inputStream = new SimpleServletInputStream(body);
        }
        return new String(super.getContentAsByteArray());
    }
}
