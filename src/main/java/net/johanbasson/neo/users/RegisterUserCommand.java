package net.johanbasson.neo.users;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserCommand {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
