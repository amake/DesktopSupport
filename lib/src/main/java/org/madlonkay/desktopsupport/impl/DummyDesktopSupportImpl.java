package org.madlonkay.desktopsupport.impl;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Window;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JMenuBar;

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

    @Override
    public void requestForeground(boolean allWindows) {
        // TODO Auto-generated method stub

    }

    @Override
    public void openHelpViewer() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefaultMenuBar(JMenuBar menuBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public Image getDockIconImage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDockIconImage(Image image) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDockIconBadge(String badge) {
        // TODO Auto-generated method stub

    }

    @Override
    public PopupMenu getDockMenu() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDockMenu(PopupMenu menu) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestUserAttention(boolean critical) {
        // TODO Auto-generated method stub

    }

    @Override
    public void requestToggleFullScreen(Window window) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setWindowCanFullScreen(Window window, boolean enabled) {
        // TODO Auto-generated method stub

    }

}
