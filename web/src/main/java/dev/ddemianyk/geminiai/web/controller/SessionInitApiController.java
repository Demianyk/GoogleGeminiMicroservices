package dev.ddemianyk.geminiai.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionInitApiController {
    @PostMapping("/init")
    public String sendMessage() {
        return "";
    }
}
