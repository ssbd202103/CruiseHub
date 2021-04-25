package pl.lodz.p.it.ssbd2021.ssbd03.entities.mok;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Login;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.Name;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.wrappers.*;

@Entity(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "Account.findByLogin", query = "SELECT acc FROM accounts acc WHERE acc.login = :login")
})
public class Account extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
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
    @Column(name = "login", nullable = false, unique = true, updatable = false)
    private String login;

    @Getter
    @Setter
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

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
    @JoinColumn(name = "language_type_id")
    @OneToOne(cascade = {CascadeType.PERSIST})
    private LanguageTypeWrapper languageType;

    @Getter
    @Setter
    @Column(name = "last_correct_authentication_date_time")
    private LocalDateTime lastCorrectAuthenticationDateTime;

    @Getter
    @Setter
    @Column(name = "last_correct_authentication_logical_address")
    private String lastCorrectAuthenticationLogicalAddress;

    @Getter
    @Setter
    @Column(name = "number_of_authentication_failures")
    private int numberOfAuthenticationFailures;

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, mappedBy = "account")
    @ToString.Exclude
    private final Set<AccessLevel> accessLevels = new HashSet<>();

    public void addAccessLevel(AccessLevel accessLevel) {
        accessLevels.add(accessLevel);
    }

    public Account() {
    }

    public Account(String firstName, String secondName, String login, String email,
                   String passwordHash, LanguageTypeWrapper languageType) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.confirmed = false;
        this.active = false;
        this.languageType = languageType;
        this.setCreatedBy(this); // Account is set as self-owner by default
        this.setAlteredBy(this);
    }

    public Account(String firstName, String secondName, String login, String email, String passwordHash,
                   boolean confirmed, boolean active, LanguageTypeWrapper languageType, Account createdBy, Account alteredBy) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.confirmed = confirmed;
        this.active = active;
        this.languageType = languageType;
        this.setCreatedBy(createdBy);
        this.setAlteredBy(alteredBy);
    }


}
