package org.madlonkay.desktopsupport.impl;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Taskbar;
import java.awt.Window;
import java.awt.desktop.AppForegroundEvent;
import java.awt.desktop.AppHiddenEvent;
import java.awt.desktop.AppReopenedEvent;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.desktop.SystemSleepEvent;
import java.awt.desktop.UserSessionEvent;
import java.io.File;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.RootPaneContainer;

import org.madlonkay.desktopsupport.AboutHandler;
import org.madlonkay.desktopsupport.AppForegroundListener;
import org.madlonkay.desktopsupport.AppHiddenListener;
import org.madlonkay.desktopsupport.AppReopenedListener;
import org.madlonkay.desktopsupport.FullScreenListener;
import org.madlonkay.desktopsupport.IDesktopSupport;
import org.madlonkay.desktopsupport.OpenFilesEvent;
import org.madlonkay.desktopsupport.OpenFilesHandler;
import org.madlonkay.desktopsupport.OpenURIHandler;
import org.madlonkay.desktopsupport.PreferencesHandler;
import org.madlonkay.desktopsupport.PrintFilesHandler;
import org.madlonkay.desktopsupport.QuitHandler;
import org.madlonkay.desktopsupport.QuitResponse;
import org.madlonkay.desktopsupport.QuitStrategy;
import org.madlonkay.desktopsupport.ScreenSleepListener;
import org.madlonkay.desktopsupport.SystemEventListener;
import org.madlonkay.desktopsupport.SystemSleepListener;
import org.madlonkay.desktopsupport.UserSessionEvent.Reason;
import org.madlonkay.desktopsupport.UserSessionListener;

public class Java9DesktopSupportImpl implements IDesktopSupport {

    public Java9DesktopSupportImpl() {
        // Call method only available in Java 9+ to test compatibility
        Taskbar.getTaskbar().getIconImage();
    }

    private final Map<SystemEventListener, java.awt.desktop.SystemEventListener> listeners = Collections
            .synchronizedMap(new IdentityHashMap<>());

    @Override
    public void addAppEventListener(SystemEventListener listener) {        
        Desktop.getDesktop().addAppEventListener(wrap(listener));
    }
    
    private java.awt.desktop.SystemEventListener wrap(SystemEventListener listener) {
        if (listener instanceof AppForegroundListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.AppForegroundListener() {
                @Override
                public void appRaisedToForeground(AppForegroundEvent e) {
                    ((AppForegroundListener) listener).appRaisedToForeground(e);
                }

                @Override
                public void appMovedToBackground(AppForegroundEvent e) {
                    ((AppForegroundListener) listener).appMovedToBackground(e);
                }
            });
        } else if (listener instanceof AppHiddenListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.AppHiddenListener() {
                @Override
                public void appHidden(AppHiddenEvent e) {
                    ((AppHiddenListener) listener).appHidden(e);
                }

                @Override
                public void appUnhidden(AppHiddenEvent e) {
                    ((AppHiddenListener) listener).appUnhidden(e);
                }
            });
        } else if (listener instanceof AppReopenedListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.AppReopenedListener() {
                @Override
                public void appReopened(AppReopenedEvent e) {
                    ((AppReopenedListener) listener).appReopened(e);
                }
            });
        } else if (listener instanceof ScreenSleepListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.ScreenSleepListener() {
                @Override
                public void screenAboutToSleep(ScreenSleepEvent e) {
                    ((ScreenSleepListener) listener).screenAboutToSleep(e);
                }

                @Override
                public void screenAwoke(ScreenSleepEvent e) {
                    ((ScreenSleepListener) listener).screenAwoke(e);
                }
            });
        } else if (listener instanceof SystemSleepListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.SystemSleepListener() {
                @Override
                public void systemAboutToSleep(SystemSleepEvent e) {
                    ((SystemSleepListener) listener).systemAboutToSleep(e);
                }

                @Override
                public void systemAwoke(SystemSleepEvent e) {
                    ((SystemSleepListener) listener).systemAwoke(e);
                }
            });
        } else if (listener instanceof UserSessionListener) {
            return listeners.computeIfAbsent(listener, (k) -> new java.awt.desktop.UserSessionListener() {
                @Override
                public void userSessionDeactivated(UserSessionEvent e) {
                    ((UserSessionListener) listener).userSessionDeactivated(() -> convert(e.getReason()));
                }

                @Override
                public void userSessionActivated(UserSessionEvent e) {
                    ((UserSessionListener) listener).userSessionActivated(() -> convert(e.getReason()));
                }
            });
        } else {
            throw new RuntimeException("Unknown listener type: " + listener.getClass());
        }
    }

    private static Reason convert(java.awt.desktop.UserSessionEvent.Reason reason) {
        switch (reason) {
        case CONSOLE:
            return Reason.CONSOLE;
        case LOCK:
            return Reason.LOCK;
        case REMOTE:
            return Reason.REMOTE;
        case UNSPECIFIED:
            return Reason.UNSPECIFIED;
        }
        throw new RuntimeException("Unknown reason: " + reason);
    }

    @Override
    public void removeAppEventListener(SystemEventListener listener) {
        java.awt.desktop.SystemEventListener wrapped = listeners.remove(listener);
        if (wrapped != null) {
            Desktop.getDesktop().removeAppEventListener(wrapped);
        }
    }

    @Override
    public void setAboutHandler(AboutHandler handler) {
        Desktop.getDesktop().setAboutHandler(evt -> handler.handleAbout(evt));
    }

    @Override
    public void setPreferencesHandler(PreferencesHandler handler) {
        Desktop.getDesktop().setPreferencesHandler(evt -> handler.handlePreferences(evt));
    }

    @Override
    public void setOpenFilesHandler(OpenFilesHandler handler) {
        Desktop.getDesktop().setOpenFileHandler(evt -> handler.openFiles(new OpenFilesEvent() {
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
    public void setPrintFilesHandler(PrintFilesHandler handler) {
        Desktop.getDesktop().setPrintFileHandler(evt -> handler.printFiles(evt::getFiles));
    }

    @Override
    public void setOpenURIHandler(OpenURIHandler handler) {
        Desktop.getDesktop().setOpenURIHandler(evt -> handler.openURI(evt::getURI));
    }

    @Override
    public void setQuitHandler(QuitHandler handler) {
        Desktop.getDesktop().setQuitHandler((evt, response) -> handler.handleQuitRequestWith(evt, new QuitResponse() {
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

    @Override
    public void requestForeground(boolean allWindows) {
        Desktop.getDesktop().requestForeground(allWindows);
    }

    @Override
    public void openHelpViewer() {
        Desktop.getDesktop().openHelpViewer();
    }

    @Override
    public void setDefaultMenuBar(JMenuBar menuBar) {
        Desktop.getDesktop().setDefaultMenuBar(menuBar);
    }

    @Override
    public Image getDockIconImage() {
        return Taskbar.getTaskbar().getIconImage();
    }

    @Override
    public void setDockIconImage(Image image) {
        Taskbar.getTaskbar().setIconImage(image);
    }

    @Override
    public void setDockIconBadge(String badge) {
        Taskbar.getTaskbar().setIconBadge(badge);
    }

    @Override
    public PopupMenu getDockMenu() {
        return Taskbar.getTaskbar().getMenu();
    }

    @Override
    public void setDockMenu(PopupMenu menu) {
        Taskbar.getTaskbar().setMenu(menu);
    }

    @Override
    public void requestUserAttention(boolean critical) {
        Taskbar.getTaskbar().requestUserAttention(true, critical);
    }

    @Override
    public void requestToggleFullScreen(Window window) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(window);
    }

    @Override
    public void setWindowCanFullScreen(Window window, boolean enabled) {
        if (window instanceof RootPaneContainer) {
            ((RootPaneContainer) window).getRootPane().putClientProperty("apple.awt.fullscreenable", enabled);
        }
    }

    @Override
    public void addFullScreenListenerTo(Window window, FullScreenListener listener) {
        // Not supported
    }

    @Override
    public void removeFullScreenListenerFrom(Window window, FullScreenListener listener) {
        // Not supported
    }
}
