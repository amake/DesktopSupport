/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.madlonkay.desktopsupport;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.madlonkay.desktopsupport.impl.AppleDesktopSupportImpl;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        try {
            new AppleDesktopSupportImpl();
        } catch (Throwable ex) {
            if (System.getProperty("java.version").startsWith("1.8")) {
                fail("Should not fail to instantiate on Java 8");
            }
        }
    }
}
