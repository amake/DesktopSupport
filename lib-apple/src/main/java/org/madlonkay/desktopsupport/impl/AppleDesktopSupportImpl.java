package org.madlonkay.desktopsupport.impl;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Window;
import java.io.File;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.LookAndFeel;

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

import com.apple.eawt.AppEvent.AppForegroundEvent;
import com.apple.eawt.AppEvent.AppHiddenEvent;
import com.apple.eawt.AppEvent.AppReOpenedEvent;
import com.apple.eawt.AppEvent.FullScreenEvent;
import com.apple.eawt.AppEvent.ScreenSleepEvent;
import com.apple.eawt.AppEvent.SystemSleepEvent;
import com.apple.eawt.AppEvent.UserSessionEvent;
import com.apple.eawt.Application;
import com.apple.eawt.FullScreenUtilities;

public class AppleDesktopSupportImpl implements IDesktopSupport {

    public AppleDesktopSupportImpl() {
        // Call method only available in Apple Java Extensions to test compatibility
        Application.getApplication().getDockIconImage();
    }

    private final Map<SystemEventListener, com.apple.eawt.AppEventListener> listeners = Collections
            .synchronizedMap(new IdentityHashMap<>());

    @Override
    public void addAppEventListener(SystemEventListener listener) {
        Application.getApplication().addAppEventListener(wrap(listener));
    }

    private com.apple.eawt.AppEventListener wrap(SystemEventListener listener) {
        if (listener instanceof AppForegroundListener) {
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.AppForegroundListener() {
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
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.AppHiddenListener() {
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
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.AppReOpenedListener() {
                @Override
                public void appReOpened(AppReOpenedEvent e) {
                    ((AppReopenedListener) listener).appReopened(e);
                }
            });
        } else if (listener instanceof ScreenSleepListener) {
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.ScreenSleepListener() {
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
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.SystemSleepListener() {
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
            return listeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.UserSessionListener() {
                @Override
                public void userSessionDeactivated(UserSessionEvent e) {
                    ((UserSessionListener) listener).userSessionDeactivated(() -> Reason.UNSPECIFIED);
                }

                @Override
                public void userSessionActivated(UserSessionEvent e) {
                    ((UserSessionListener) listener).userSessionActivated(() -> Reason.UNSPECIFIED);
                }
            });
        } else {
            throw new RuntimeException("Unknown listener type: " + listener.getClass());
        }
    }

    private final Map<FullScreenListener, com.apple.eawt.FullScreenListener> fsListeners = Collections
            .synchronizedMap(new IdentityHashMap<>());

    private com.apple.eawt.FullScreenListener wrap(FullScreenListener listener) {
        return fsListeners.computeIfAbsent(listener, (k) -> new com.apple.eawt.FullScreenListener() {
            @Override
            public void windowEnteringFullScreen(FullScreenEvent evt) {
                listener.windowEnteringFullScreen(evt::getWindow);
            }

            @Override
            public void windowEnteredFullScreen(FullScreenEvent evt) {
                listener.windowEnteredFullScreen(evt::getWindow);
            }

            @Override
            public void windowExitingFullScreen(FullScreenEvent evt) {
                listener.windowExitingFullScreen(evt::getWindow);
            }

            @Override
            public void windowExitedFullScreen(FullScreenEvent evt) {
                listener.windowExitedFullScreen(evt::getWindow);
            }
        });
    }

    @Override
    public void removeAppEventListener(SystemEventListener listener) {
        com.apple.eawt.AppEventListener wrapped = listeners.remove(listener);
        if (wrapped != null) {
            Application.getApplication().removeAppEventListener(wrapped);
        }
    }

    @Override
    public void setAboutHandler(AboutHandler handler) {
        Application.getApplication().setAboutHandler(evt -> handler.handleAbout(evt));
    }

    @Override
    public void setPreferencesHandler(PreferencesHandler handler) {
        Application.getApplication().setPreferencesHandler(evt -> handler.handlePreferences(evt));
    }

    @Override
    public void setOpenFilesHandler(OpenFilesHandler handler) {
        Application.getApplication().setOpenFileHandler(evt -> handler.openFiles(new OpenFilesEvent() {
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
        Application.getApplication().setPrintFileHandler(evt -> handler.printFiles(evt::getFiles));
    }

    @Override
    public void setOpenURIHandler(OpenURIHandler handler) {
        Application.getApplication().setOpenURIHandler(evt -> handler.openURI(evt::getURI));
    }

    @Override
    public void setQuitHandler(QuitHandler handler) {
        Application.getApplication()
                .setQuitHandler((evt, response) -> handler.handleQuitRequestWith(evt, new QuitResponse() {
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

    @Override
    public void requestForeground(boolean allWindows) {
        Application.getApplication().requestForeground(allWindows);
    }

    @Override
    public void openHelpViewer() {
        Application.getApplication().openHelpViewer();
    }

    @Override
    public void setDefaultMenuBar(JMenuBar menuBar) {
        Application.getApplication().setDefaultMenuBar(menuBar);
    }

    @Override
    public Image getDockIconImage() {
        return Application.getApplication().getDockIconImage();
    }

    @Override
    public void setDockIconImage(Image image) {
        Application.getApplication().setDockIconImage(image);
    }

    @Override
    public void setDockIconBadge(String badge) {
        Application.getApplication().setDockIconBadge(badge);
    }

    @Override
    public PopupMenu getDockMenu() {
        return Application.getApplication().getDockMenu();
    }

    @Override
    public void setDockMenu(PopupMenu menu) {
        Application.getApplication().setDockMenu(menu);
    }

    @Override
    public void requestUserAttention(boolean critical) {
        Application.getApplication().requestUserAttention(critical);
    }

    @Override
    public void requestToggleFullScreen(Window window) {
        Application.getApplication().requestToggleFullScreen(window);
    }

    @Override
    public void setWindowCanFullScreen(Window window, boolean enabled) {
        FullScreenUtilities.setWindowCanFullScreen(window, enabled);
    }

    @Override
    public void addFullScreenListenerTo(Window window, FullScreenListener listener) {
        FullScreenUtilities.addFullScreenListenerTo(window, wrap(listener));
    }

    @Override
    public void removeFullScreenListenerFrom(Window window, FullScreenListener listener) {
        com.apple.eawt.FullScreenListener wrapped = fsListeners.remove(listener);
        if (wrapped != null) {
            FullScreenUtilities.removeFullScreenListenerFrom(window, wrapped);
        }
    }

    @Override
    public LookAndFeel createLookAndFeel(String name) throws Exception {
        return (LookAndFeel) Class.forName(name).getDeclaredConstructor().newInstance();
    }
}
