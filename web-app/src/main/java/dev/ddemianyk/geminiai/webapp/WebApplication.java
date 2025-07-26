package dev.ddemianyk.geminiai.webapp;

import dev.ddemianyk.geminiai.web.WebPackageScanMarker;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        WebPackageScanMarker.class,
})
public class WebApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(WebApplication.class, args);
    }
}
