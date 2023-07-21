package com.project.enderman.repositories;

import com.project.enderman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM user_ u WHERE u.username = :username")
    public List<User> findByUsername(@Param("username") String username);

}
