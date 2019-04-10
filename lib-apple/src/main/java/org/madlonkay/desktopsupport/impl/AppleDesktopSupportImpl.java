package org.madlonkay.desktopsupport.impl;

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

import com.apple.eawt.Application;

public class AppleDesktopSupportImpl implements IDesktopSupport {

    public AppleDesktopSupportImpl() {
        Application.getApplication().setAboutHandler(null);
    }

    @Override
    public void setAboutHandler(Consumer<Object> handler) {
        Application.getApplication().setAboutHandler(evt -> handler.accept(evt));
    }

    @Override
    public void setPreferencesHandler(Consumer<Object> handler) {
        Application.getApplication().setPreferencesHandler(evt -> handler.accept(evt));
    }

    @Override
    public void setOpenFilesHandler(Consumer<OpenFilesEvent> handler) {
        Application.getApplication().setOpenFileHandler(evt -> handler.accept(new OpenFilesEvent() {
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
        Application.getApplication().setPrintFileHandler(evt -> handler.accept(evt::getFiles));
    }

    @Override
    public void setOpenURIHandler(Consumer<OpenURIEvent> handler) {
        Application.getApplication().setOpenURIHandler(evt -> handler.accept(evt::getURI));
    }

    @Override
    public void setQuitHandler(BiConsumer<Object, QuitResponse> handler) {
        Application.getApplication().setQuitHandler((evt, response) -> handler.accept(evt, new QuitResponse() {
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
        Application.getApplication().setQuitStrategy(convert(strategy));
    }

    private static com.apple.eawt.QuitStrategy convert(QuitStrategy strategy) {
        switch (strategy) {
        case CLOSE_ALL_WINDOWS:
            return com.apple.eawt.QuitStrategy.CLOSE_ALL_WINDOWS;
        case NORMAL_EXIT:
            return com.apple.eawt.QuitStrategy.SYSTEM_EXIT_0;
        }
        throw new RuntimeException("Unknown strategy: " + strategy);
    }

    @Override
    public void enableSuddenTermination() {
        Application.getApplication().enableSuddenTermination();
    }

    @Override
    public void disableSuddenTermination() {
        Application.getApplication().disableSuddenTermination();
    }
}
