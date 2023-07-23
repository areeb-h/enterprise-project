package com.example.enterpriseproject.repository;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.enterpriseproject.model.Role;
import com.example.enterpriseproject.model.User;

//repository to communicate with db
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @NotNull
    Optional<User> findById(@NotNull Long id);

    // @NotNull List<User> findAll();

    List<User> findAllByRoleAndEnabled(Role role, Boolean enabled);

    List<User> findAllByRoleAndEnabledAndLocked(Role role, Boolean enabled, Boolean locked);

    List<User> findAllByEnabled(Boolean enabled);

    List<User> findAllByEnabledAndLocked(Boolean enabled, Boolean locked);

}
