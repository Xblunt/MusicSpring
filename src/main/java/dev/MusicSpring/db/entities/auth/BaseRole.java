package dev.MusicSpring.db.entities.auth;

import lombok.Getter;

@Getter
public enum BaseRole {
    SUPER_USER("ROLE_SUPER_USER", "SUPER_USER"),
    USERS("ROLE_USERS", "USERS");

    private final String value;
    private final String role;
     BaseRole(String value, String role) {
        this.value = value;
        this.role = role;

    }}
