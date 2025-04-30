package com.accenture.user_microservice.models;

import com.accenture.user_microservice.models.enums.RoleType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Setter
    @Column(nullable = false)
    private String username;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    public UserEntity() {
    }

    public UserEntity(String username, String email, String password, RoleType roleType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roleType=" + roleType +
                '}';
    }
}
