package com.project.demo.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class SignUp {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private List<String> role;
    @NotNull
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
