package pl.lodz.p.it.ssbd2021.ssbd03.mok.model;

import java.util.Set;

public class Account extends EntityDetails {

    private String firstName;
    private String secondName;

    private String login;
    private String email;
    private String password;

    private boolean confirmed;
    private boolean active;

    private Set<AccessLevel> accessLevels;
}
