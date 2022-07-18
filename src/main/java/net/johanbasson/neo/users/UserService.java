package net.johanbasson.neo.users;

import lombok.AllArgsConstructor;
import net.johanbasson.neo.common.ApiError;
import net.johanbasson.neo.core.EventDispatcher;
import net.johanbasson.neo.core.bus.CommandHandler;
import net.johanbasson.neo.core.bus.EventBus;
import net.johanbasson.neo.core.bus.QueryHandler;
import net.johanbasson.neo.security.JwtTokenService;
import net.johanbasson.neo.security.UserDetailsImpl;
import net.johanbasson.rail.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public final class UserService {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final JwtTokenService jwtTokenService;

    @Autowired
    private final EventDispatcher eventDispatcher;


    @QueryHandler
    public JwtResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenService.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles);
        } catch (BadCredentialsException ex) {
            throw new ApiError("Bad credentials");
        }
    }

    public void register(RegisterUserCommand command) {
        Optional<User> maybeUser = userRepository.findByEmail(command.getEmail());
        if (maybeUser.isPresent()) {
            throw new ApiError("User already exists");
        }

        User u = new User(UUID.randomUUID(), command.getEmail(), encoder.encode(command.getPassword()), Collections.singleton(Role.USER));
        eventDispatcher.publish(new UserRegisteredEvent(u));
    }
}
