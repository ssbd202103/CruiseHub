package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "accounts")
public class Account extends EntityDetails {
    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String login;
    private String email;

    @Column(name = "password_hash")
    private String password;

    private boolean confirmed;
    private boolean active;

    @Column(name = "last_incorrect_authentication_date_time")
    private LocalDateTime lastIncorrectAuthenticationDateTime;

    @Column(name = "last_incorrect_authentication_logical_address")
    private String lastIncorrectAuthenticationLogicalAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_type")
    private LanguageType languageType;

    @Column(name = "last_correct_authentication_date_time")
    private LocalDateTime lastCorrectAuthenticationDateTime;

    @Column(name = "last_correct_authentication_logical_address")
    private String lastCorrectAuthenticationLogicalAddress;


    @OneToMany(mappedBy = "account")
    private List<AccessLevel> accessLevels = new ArrayList<>();
}
