/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.madlonkay.desktopsupport;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.madlonkay.desktopsupport.impl.AppleDesktopSupportImpl;
import org.madlonkay.desktopsupport.impl.DummyDesktopSupportImpl;
import org.madlonkay.desktopsupport.impl.Java9DesktopSupportImpl;

public final class DesktopSupport {

    private static final Logger LOGGER = Logger.getLogger(DesktopSupport.class.getName());

    private static IDesktopSupport impl;

    public static synchronized IDesktopSupport getSupport() {
        if (impl == null) {
            impl = getSupportImpl();
            LOGGER.log(Level.FINEST, "Using DesktopSupport implementation: " + impl.getClass().getName());
        }
        return impl;
    }

    private static IDesktopSupport getSupportImpl() {
        try {
            return new Java9DesktopSupportImpl();
        } catch (Throwable ex) {
            LOGGER.log(Level.FINEST, "Could not instantiate Java 9 support");
        }
        try {
            return new AppleDesktopSupportImpl();
        } catch (Throwable ex) {
            LOGGER.log(Level.FINEST, "Could not instantiate Apple support");
        }
        return new DummyDesktopSupportImpl();
    }

    private DesktopSupport() {
    }
}
