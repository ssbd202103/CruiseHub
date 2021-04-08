package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Getter;
import lombok.Setter;
import validators.Login;
import validators.Name;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "accounts")
public class Account extends EntityDetails {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Name
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Name
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Getter
    @Setter
    @Login
    @Column(name = "login", nullable = false)
    private String login;

    @Getter
    @Setter
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Getter
    @Setter
    @Size(min = 8, max = 64)
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(name = "confirmed", nullable = false)
    private boolean confirmed;

    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    @Getter
    @Setter
    @Column(name = "last_incorrect_authentication_date_time")
    private LocalDateTime lastIncorrectAuthenticationDateTime;

    @Getter
    @Setter
    @Column(name = "last_incorrect_authentication_logical_address")
    private String lastIncorrectAuthenticationLogicalAddress;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "language_type", nullable = false)
    private LanguageType languageType;

    @Getter
    @Setter
    @Column(name = "last_correct_authentication_date_time")
    private LocalDateTime lastCorrectAuthenticationDateTime;

    @Getter
    @Setter
    @Column(name = "last_correct_authentication_logical_address")
    private String lastCorrectAuthenticationLogicalAddress;

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "account_id")
    private final List<AccessLevel> accessLevels = new ArrayList<>();

    public void addAccessLevel(AccessLevel accessLevel) {
        accessLevels.add(accessLevel);
    }

}
