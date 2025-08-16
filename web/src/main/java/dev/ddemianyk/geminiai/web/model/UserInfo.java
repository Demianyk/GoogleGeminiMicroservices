package dev.ddemianyk.geminiai.web.model;

import java.util.Set;

public record UserInfo(String email, Set<String> authorities) {
}
