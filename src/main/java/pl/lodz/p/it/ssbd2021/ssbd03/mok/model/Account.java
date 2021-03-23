package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Account extends EntityDetails {
    private String firstName;
    private String secondName;

    private String login;
    private String email;
    private String password;

    private boolean confirmed;
    private boolean active;

    private LocalDateTime lastIncorrectAuthenticationDateTime;
    private String lastIncorrectAuthenticationLogicalAdress;
    private LanguageType languageType;

    private LocalDateTime lastCorrectAuthenticationDateTime;
    private String lastCorrectAuthenticationLogicalAdress;


    private Set<AccessLevel> accessLevels;
}
