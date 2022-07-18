package net.johanbasson.neo.users;

import lombok.AllArgsConstructor;
import net.johanbasson.neo.core.bus.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserView {

    private UserRepository userRepository;

    @EventHandler
    public void handle(UserRegisteredEvent event) {
        userRepository.insert(event.getUser());
    }
}
