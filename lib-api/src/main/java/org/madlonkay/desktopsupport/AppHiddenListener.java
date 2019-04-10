package org.madlonkay.desktopsupport;

public interface AppHiddenListener extends SystemEventListener {
    public void appHidden(Object e);

    public void appUnhidden(Object e);
}