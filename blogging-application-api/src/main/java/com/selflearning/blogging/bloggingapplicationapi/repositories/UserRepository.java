package com.selflearning.blogging.bloggingapplicationapi.repositories;

import com.selflearning.blogging.bloggingapplicationapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByEmail(String email);
}
