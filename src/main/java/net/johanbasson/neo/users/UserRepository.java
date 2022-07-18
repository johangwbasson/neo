package net.johanbasson.neo.users;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    void insert(User u);
}
