package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import lombok.Data;
import validators.Login;
import validators.Name;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "accounts")
public class Account {
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Name
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Name
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Login
    @Column(name = "login", nullable = false)
    private String login;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 8, max = 64)
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

    @Version
    private Long version;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<AccessLevel> accessLevels = new ArrayList<>();
}
