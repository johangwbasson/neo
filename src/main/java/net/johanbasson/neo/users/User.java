package net.johanbasson.neo.users;

import java.util.Set;
import java.util.UUID;

public record User(UUID id, String email, String hash, Set<Role> roles) {
}
