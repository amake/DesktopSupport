/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.madlonkay.desktopsupport;

import org.junit.Test;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        IDesktopSupport classUnderTest = DesktopSupport.getSupport();
        classUnderTest.setAboutHandler(e -> {
        });
    }
}
