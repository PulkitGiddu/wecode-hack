package com.wecode.bookit.repository;

import com.wecode.bookit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(User.UserRole role);

    @Query("SELECT u FROM User u WHERE u.role = 'MANAGER'")
    List<User> findAllManagers();

    boolean existsByEmail(String email);
}