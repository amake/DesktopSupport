package org.madlonkay.desktopsupport;

public interface SystemSleepListener extends SystemEventListener {
    void systemAboutToSleep(Object e);

    void systemAwoke(Object e);
}