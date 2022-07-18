package net.johanbasson.neo.users;

import java.util.List;
import java.util.UUID;

public record JwtResponse(String accessToken, UUID id, String username, String email, List<String> roles) {
}
