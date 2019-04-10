package org.madlonkay.desktopsupport;

public interface UserSessionListener extends SystemEventListener {
    void userSessionDeactivated(UserSessionEvent e);

    void userSessionActivated(UserSessionEvent e);
}