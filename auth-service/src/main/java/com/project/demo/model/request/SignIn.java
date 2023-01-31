package com.project.demo.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignIn {
    @NotBlank
    @NotNull
    @Size(min = 6, max = 20)
    private String input;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 40)
    private String password;
}
