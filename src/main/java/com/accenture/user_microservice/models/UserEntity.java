package com.accenture.user_microservice.models;

import com.accenture.user_microservice.models.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
