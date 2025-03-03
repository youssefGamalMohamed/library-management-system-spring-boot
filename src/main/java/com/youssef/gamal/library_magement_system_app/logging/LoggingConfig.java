package com.youssef.gamal.library_magement_system_app.logging;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;

@Configuration
public class LoggingConfig {

    @Bean
    public Logbook logbookBean() {
        return Logbook.builder()
                .sink(
                        new DefaultSink(
                                new CustomHttpLogFormatter(new DefaultHttpLogFormatter()),
                                new DefaultHttpLogWriter()
                        )
                )
                .build();
    }

     static class CustomHttpLogFormatter implements HttpLogFormatter {
        private final HttpLogFormatter delegate;

        CustomHttpLogFormatter(HttpLogFormatter delegate) {
            this.delegate = delegate;
        }


         @Override
         public String format(@Nonnull Precorrelation precorrelation, @Nonnull HttpRequest request) throws IOException {
             return  "\n" + Strings.repeat("=", 200) + "\n" +
                      "REQUEST >>> \n" + delegate.format(precorrelation, request) + "\n";
         }

         @Override
         public String format(@Nonnull Correlation correlation, @Nonnull HttpResponse response) throws IOException {
             return "\n" + "RESPONSE >>> \n" + delegate.format(correlation, response) +  "\n" + Strings.repeat("=", 200) + "\n" ;

         }
     }
}
