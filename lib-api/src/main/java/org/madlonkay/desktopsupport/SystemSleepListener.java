package org.madlonkay.desktopsupport;

import java.util.EventObject;

public interface SystemSleepListener extends SystemEventListener {
    void systemAboutToSleep(EventObject e);

    void systemAwoke(EventObject e);
}
