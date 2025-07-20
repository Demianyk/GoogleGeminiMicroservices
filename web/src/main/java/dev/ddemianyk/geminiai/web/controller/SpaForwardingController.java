package dev.ddemianyk.geminiai.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaForwardingController {

    // For requests like /spa or /spa/anything (but NOT /api/** or real static files)
    @GetMapping("/spa/**")
    public String forwardToSpa() {
        return "forward:/index.html";
    }
}
