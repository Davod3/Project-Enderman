package com.project.enderman.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Entity(name = "user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String username;

    private String token;

    public User() {
        //Empty on purpose
    }

    public User(String username) {

        this.username = username;
        this.token = generateToken(username);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String generateToken(String username) {
        Random rnd = new Random();
        SecureRandom gen = new SecureRandom();
        byte[] bytes = new byte[20];

        gen.nextBytes(bytes);
        long nonce = rnd.nextLong(42);

        String token = Base64.getEncoder().encodeToString(bytes);

        return token + Base64.getEncoder().encodeToString(String.valueOf(username.hashCode()*nonce).getBytes());
    }

}
