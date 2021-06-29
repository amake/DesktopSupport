package org.madlonkay.desktopsupport;

import java.util.EventObject;

public interface AppForegroundListener extends SystemEventListener {
    public void appRaisedToForeground(EventObject e);

    public void appMovedToBackground(EventObject e);
}
