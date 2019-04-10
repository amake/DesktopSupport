package org.madlonkay.desktopsupport.impl;

import java.awt.Desktop;
import java.awt.event.ActionListener;

import org.madlonkay.desktopsupport.IDesktopSupport;

public class Java9DesktopSupportImpl implements IDesktopSupport {

    public Java9DesktopSupportImpl() {
        Desktop.getDesktop();
    }

    public void setAboutHandler(ActionListener handler) {
        Desktop.getDesktop().setAboutHandler(e -> handler.actionPerformed(null));
    }
}
