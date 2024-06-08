package com.sprinsecurity.security.dao;

import com.sprinsecurity.security.entity.VerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyTokenDao extends JpaRepository<VerifyToken,Integer> {
    VerifyToken findByToken(String token);
}
