package dev.ddemianyk.geminiai.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("dev") // Only apply this configuration in the 'dev' profile
public class WebConfig implements WebMvcConfigurer {

    @Value("${front-end-url:}")
    private String spaUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(spaUrl)
                .allowedMethods("GET")
                .allowCredentials(true);
    }
}
