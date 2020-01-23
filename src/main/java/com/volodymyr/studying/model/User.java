package com.volodymyr.studying.model;

import com.volodymyr.studying.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@PasswordMatches
public class User {

    //TODO where should I save salt? In DB?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    @Transient
    private String matchingPassword;

    @Email
    private String email;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;
}
