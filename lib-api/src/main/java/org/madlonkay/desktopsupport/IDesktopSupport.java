package org.madlonkay.desktopsupport;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Window;
import java.util.Objects;

import javax.swing.JMenuBar;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public interface IDesktopSupport {
    void addAppEventListener(SystemEventListener listener);

    void removeAppEventListener(SystemEventListener listener);

    void setAboutHandler(AboutHandler handler);

    void setPreferencesHandler(PreferencesHandler handler);

    void setOpenFilesHandler(OpenFilesHandler handler);

    void setPrintFilesHandler(PrintFilesHandler handler);

    void setOpenURIHandler(OpenURIHandler handler);

    void setQuitHandler(QuitHandler handler);

    void setQuitStrategy(QuitStrategy strategy);

    void enableSuddenTermination();

    void disableSuddenTermination();

    void requestForeground(boolean allWindows);

    void openHelpViewer();

    void setDefaultMenuBar(JMenuBar menuBar);

    // Methods moved to Taskbar in Java 9

    Image getDockIconImage();

    void setDockIconImage(Image image);

    void setDockIconBadge(String badge);

    PopupMenu getDockMenu();

    void setDockMenu(PopupMenu menu);

    void requestUserAttention(boolean critical);

    void requestToggleFullScreen(Window window);

    // Methods on FullScreenUtilities

    void setWindowCanFullScreen(Window window, boolean enabled);

    void addFullScreenListenerTo(Window window, FullScreenListener listener);

    void removeFullScreenListenerFrom(Window window, FullScreenListener listener);

    // Methods on UIManager

    default LookAndFeel createLookAndFeel(String name) throws UnsupportedLookAndFeelException {
        Objects.requireNonNull(name);
        if ("GTK look and feel".equals(name)) {
            name = "GTK+";
        }
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals(name)) {
                    LookAndFeel laf = (LookAndFeel) Class.forName(info.getClassName()).getDeclaredConstructor()
                            .newInstance();
                    if (!laf.isSupportedLookAndFeel()) {
                        break;
                    }
                    return laf;
                }
            }
        } catch (ReflectiveOperationException | IllegalArgumentException ignore) {
        }
        throw new UnsupportedLookAndFeelException(name);
    }
}
