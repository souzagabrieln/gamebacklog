package com.gabriel.gamebacklog.model;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    public User findByUsername(String username);

    boolean existsByUsername(String username);

}
