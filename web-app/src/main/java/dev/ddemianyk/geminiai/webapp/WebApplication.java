package dev.ddemianyk.geminiai.webapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "dev.ddemianyk.geminiai.web",
})
public class WebApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(WebApplication.class, args);
    }
}
