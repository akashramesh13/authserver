package com.akash.authserver.repository;

import com.akash.authserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u from User u where u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
