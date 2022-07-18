package net.johanbasson.neo.users;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public final class UserController {

    private final UserService userService;

    @PostMapping("/api/authenticate")
    public JwtResponse login(@Valid @RequestBody LoginRequest request) {
         return userService.authenticate(request);
    }

    @PostMapping("/api/users/register")
    public void register(@Valid @RequestBody RegisterUserCommand request) {
        userService.register(request);
    }

}
