package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "last_incorrect_authentication_date_time")
    private LocalDateTime lastIncorrectAuthenticationDateTime;

    @Column(name = "last_incorrect_authentication_logical_address")
    private String lastIncorrectAuthenticationLogicalAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_type", nullable = false)
    private LanguageType languageType;

    @Column(name = "last_correct_authentication_date_time")
    private LocalDateTime lastCorrectAuthenticationDateTime;

    @Column(name = "last_correct_authentication_logical_address")
    private String lastCorrectAuthenticationLogicalAddress;

    @Embedded
    private EntityDetails entityDetails;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<AccessLevel> accessLevels = new ArrayList<>();
}
