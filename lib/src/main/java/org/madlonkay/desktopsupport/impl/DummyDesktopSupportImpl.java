package org.madlonkay.desktopsupport.impl;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.madlonkay.desktopsupport.FilesEvent;
import org.madlonkay.desktopsupport.IDesktopSupport;
import org.madlonkay.desktopsupport.OpenFilesEvent;
import org.madlonkay.desktopsupport.OpenURIEvent;
import org.madlonkay.desktopsupport.QuitResponse;
import org.madlonkay.desktopsupport.QuitStrategy;
import org.madlonkay.desktopsupport.SystemEventListener;

public class DummyDesktopSupportImpl implements IDesktopSupport {

    @Override
    public void addAppEventListener(SystemEventListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAppEventListener(SystemEventListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAboutHandler(Consumer<Object> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPreferencesHandler(Consumer<Object> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setOpenFilesHandler(Consumer<OpenFilesEvent> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPrintFilesHandler(Consumer<FilesEvent> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setOpenURIHandler(Consumer<OpenURIEvent> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setQuitHandler(BiConsumer<Object, QuitResponse> handler) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setQuitStrategy(QuitStrategy strategy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enableSuddenTermination() {
        // TODO Auto-generated method stub

    }

    @Override
    public void disableSuddenTermination() {
        // TODO Auto-generated method stub

    }

}
