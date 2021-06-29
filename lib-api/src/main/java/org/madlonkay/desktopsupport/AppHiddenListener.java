package org.madlonkay.desktopsupport;

import java.util.EventObject;

public interface AppHiddenListener extends SystemEventListener {
    public void appHidden(EventObject e);

    public void appUnhidden(EventObject e);
}
