package org.madlonkay.desktopsupport;

import java.util.EventObject;

public interface ScreenSleepListener extends SystemEventListener {
    void screenAboutToSleep(EventObject e);

    void screenAwoke(EventObject e);
}
