package org.madlonkay.desktopsupport;

public interface UserSessionEvent {
    public static enum Reason {
        UNSPECIFIED, CONSOLE, REMOTE, LOCK
    }

    Reason getReason();
}
