package org.madlonkay.desktopsupport;

public interface AppForegroundListener extends SystemEventListener {
    public void appRaisedToForeground(Object e);

    public void appMovedToBackground(Object e);
}