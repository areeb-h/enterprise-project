package com.example.enterpriseproject.repository;

import com.example.enterpriseproject.model.Role;
import com.example.enterpriseproject.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//repository to communicate with db
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @NotNull
    Optional<User> findById(@NotNull Long id);

    // @NotNull List<User> findAll();

    List<User> findAllByRoleAndEnabled(Role role, Boolean enabled);
}
