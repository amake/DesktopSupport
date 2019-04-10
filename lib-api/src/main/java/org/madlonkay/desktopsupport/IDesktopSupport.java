package org.madlonkay.desktopsupport;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Window;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JMenuBar;

public interface IDesktopSupport {
    void addAppEventListener(SystemEventListener listener);

    void removeAppEventListener(SystemEventListener listener);

    void setAboutHandler(Consumer<Object> handler);

    void setPreferencesHandler(Consumer<Object> handler);

    void setOpenFilesHandler(Consumer<OpenFilesEvent> handler);

    void setPrintFilesHandler(Consumer<FilesEvent> handler);

    void setOpenURIHandler(Consumer<OpenURIEvent> handler);

    void setQuitHandler(BiConsumer<Object, QuitResponse> handler);

    void setQuitStrategy(QuitStrategy strategy);

    void enableSuddenTermination();

    void disableSuddenTermination();

    void requestForeground(boolean allWindows);

    void openHelpViewer();

    void setDefaultMenuBar(JMenuBar menuBar);

    // Methods moved to Taskbar in Java

    Image getDockIconImage();

    void setDockIconImage(Image image);

    void setDockIconBadge(String badge);

    PopupMenu getDockMenu();

    void setDockMenu(PopupMenu menu);

    void requestUserAttention(boolean critical);

    void requestToggleFullScreen(Window window);

    // Methods on FullScreenUtilities

    void setWindowCanFullScreen(Window window, boolean enabled);
}
