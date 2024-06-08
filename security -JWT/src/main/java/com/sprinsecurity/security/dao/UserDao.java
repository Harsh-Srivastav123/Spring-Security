package com.sprinsecurity.security.dao;

import com.sprinsecurity.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String> {

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findUsersByRole(@Param("role") String role);

    Optional<User> findByUserName(String username);
}
