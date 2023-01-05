package com.dora.weblab4.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "weblab4_user")
public class WebUserModel implements WebUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "weblab4_user_id", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String encodedPassword;

    @OneToMany(mappedBy = "user")
    private List<CheckModel> checks;

    public WebUserModel() {}

    private String encode(String password) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }

    public boolean validatePassword(String rawPassword) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

    public WebUserModel(String username, String password) {
        this.username = username;
        this.encodedPassword = encode(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public List<CheckModel> getChecks() {
        return checks;
    }
}
