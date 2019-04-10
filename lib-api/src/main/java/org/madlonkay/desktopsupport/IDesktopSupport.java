package org.madlonkay.desktopsupport;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IDesktopSupport {
    void setAboutHandler(Consumer<Object> handler);

    void setPreferencesHandler(Consumer<Object> handler);

    void setOpenFilesHandler(Consumer<OpenFilesEvent> handler);

    void setPrintFilesHandler(Consumer<FilesEvent> handler);

    void setOpenURIHandler(Consumer<OpenURIEvent> handler);

    void setQuitHandler(BiConsumer<Object, QuitResponse> handler);

    void setQuitStrategy(QuitStrategy strategy);

    void enableSuddenTermination();

    void disableSuddenTermination();
}
