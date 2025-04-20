package com.example.my_sb_app.repository;

import com.example.my_sb_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String paramString);

    User findByActivationCode(String paramString);
}
