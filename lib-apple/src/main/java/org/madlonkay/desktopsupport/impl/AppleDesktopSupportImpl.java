package org.madlonkay.desktopsupport.impl;

import java.awt.event.ActionListener;

import org.madlonkay.desktopsupport.IDesktopSupport;

import com.apple.eawt.Application;

public class AppleDesktopSupportImpl implements IDesktopSupport {

    public AppleDesktopSupportImpl() {
        Application.getApplication().setAboutHandler(null);
    }

    public void setAboutHandler(ActionListener handler) {
        Application.getApplication().setAboutHandler(e -> handler.actionPerformed(null));
    }
}
