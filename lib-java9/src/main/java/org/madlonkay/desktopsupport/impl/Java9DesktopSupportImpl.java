package org.madlonkay.desktopsupport.impl;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.madlonkay.desktopsupport.FilesEvent;
import org.madlonkay.desktopsupport.IDesktopSupport;
import org.madlonkay.desktopsupport.OpenFilesEvent;
import org.madlonkay.desktopsupport.OpenURIEvent;
import org.madlonkay.desktopsupport.QuitResponse;
import org.madlonkay.desktopsupport.QuitStrategy;

public class Java9DesktopSupportImpl implements IDesktopSupport {

    public Java9DesktopSupportImpl() {
        Desktop.getDesktop();
    }

    @Override
    public void setAboutHandler(Consumer<Object> handler) {
        Desktop.getDesktop().setAboutHandler(evt -> handler.accept(evt));
    }

    @Override
    public void setPreferencesHandler(Consumer<Object> handler) {
        Desktop.getDesktop().setPreferencesHandler(evt -> handler.accept(evt));
    }

    @Override
    public void setOpenFilesHandler(Consumer<OpenFilesEvent> handler) {
        Desktop.getDesktop().setOpenFileHandler(evt -> handler.accept(new OpenFilesEvent() {
            @Override
            public List<File> getFiles() {
                return evt.getFiles();
            }

            @Override
            public String getSearchTerm() {
                return evt.getSearchTerm();
            }
        }));
    }

    @Override
    public void setPrintFilesHandler(Consumer<FilesEvent> handler) {
        Desktop.getDesktop().setPrintFileHandler(evt -> handler.accept(evt::getFiles));
    }

    @Override
    public void setOpenURIHandler(Consumer<OpenURIEvent> handler) {
        Desktop.getDesktop().setOpenURIHandler(evt -> handler.accept(evt::getURI));
    }

    @Override
    public void setQuitHandler(BiConsumer<Object, QuitResponse> handler) {
        Desktop.getDesktop().setQuitHandler((evt, response) -> handler.accept(evt, new QuitResponse() {
            @Override
            public void performQuit() {
                response.performQuit();
            }

            @Override
            public void cancelQuit() {
                response.cancelQuit();
            }
        }));
    }

    @Override
    public void setQuitStrategy(QuitStrategy strategy) {
        Desktop.getDesktop().setQuitStrategy(convert(strategy));
    }

    private static java.awt.desktop.QuitStrategy convert(QuitStrategy strategy) {
        switch (strategy) {
        case CLOSE_ALL_WINDOWS:
            return java.awt.desktop.QuitStrategy.CLOSE_ALL_WINDOWS;
        case NORMAL_EXIT:
            return java.awt.desktop.QuitStrategy.NORMAL_EXIT;
        }
        throw new RuntimeException("Unknown strategy: " + strategy);
    }

    @Override
    public void enableSuddenTermination() {
        Desktop.getDesktop().enableSuddenTermination();
    }

    @Override
    public void disableSuddenTermination() {
        Desktop.getDesktop().disableSuddenTermination();
    }
}
