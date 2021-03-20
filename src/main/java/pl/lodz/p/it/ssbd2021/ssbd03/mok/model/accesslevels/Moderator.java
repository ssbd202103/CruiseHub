package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.accesslevels;

import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevel;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.AccessLevelType;

public class Moderator extends AccessLevel {
    @Override
    public AccessLevelType getAccessLevelType() {
        return AccessLevelType.MODERATOR;
    }
}