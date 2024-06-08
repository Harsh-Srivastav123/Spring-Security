package com.sprinsecurity.security.model;

import lombok.Data;

@Data
public class LoginPayload {
    String userName;
    String password;
}
