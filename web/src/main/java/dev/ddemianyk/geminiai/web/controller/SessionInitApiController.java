package dev.ddemianyk.geminiai.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionInitApiController {

    /**
     * CSRF protection requires a modifying request to have a CSRF token. However, the cookie with the CSRF token
     * is not available until the user, which is logged in, makes the first modifying request to the server.
     * This endpoint is used to send a request that initializes the CSRF token cookie. It will respond with a 403 status,
     * but will set the CSRF token cookie in the response headers.
     */
    @PostMapping("/init")
    public String sendMessage() {
        return "";
    }
}
