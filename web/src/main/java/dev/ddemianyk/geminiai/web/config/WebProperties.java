package dev.ddemianyk.geminiai.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebProperties {

    @Value("${front-end-url:}")
    @Getter
    private String spaUrl;
}
