package org.madlonkay.desktopsupport;

import java.util.EventListener;

public interface FullScreenListener extends EventListener {
    void windowEnteringFullScreen(FullScreenEvent e);

    void windowEnteredFullScreen(FullScreenEvent e);

    void windowExitingFullScreen(FullScreenEvent e);

    void windowExitedFullScreen(FullScreenEvent e);
}