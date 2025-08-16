package dev.ddemianyk.geminiai.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Profile("dev") // Only apply this configuration in the 'dev' profile
public class WebConfig implements WebMvcConfigurer {

    private final WebProperties webProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(webProperties.getSpaUrl())
                .allowedMethods("GET", "POST", "DELETE")
                .allowCredentials(true);
    }
}
