package com.sprinsecurity.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String userId;
    String userName;
    String password;
    String email;
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    List<String> roles;
}
