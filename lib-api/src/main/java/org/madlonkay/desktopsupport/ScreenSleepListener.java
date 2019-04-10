package org.madlonkay.desktopsupport;

public interface ScreenSleepListener extends SystemEventListener {
    void screenAboutToSleep(Object e);

    void screenAwoke(Object e);
}