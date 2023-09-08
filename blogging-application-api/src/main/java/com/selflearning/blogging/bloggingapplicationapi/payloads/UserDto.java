package com.selflearning.blogging.bloggingapplicationapi.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto
{
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Please enter a valid email address")
    @NotEmpty
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 3, max = 10, message = "Password must have minimum 3 and maximum 10 letters")
//    @Pattern(regexp = ) : To enforce strong password
    private String password;

    @NotEmpty(message = "About cannot be empty")
    private String about;
}
