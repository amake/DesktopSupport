/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.madlonkay.desktopsupport.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class CliAppTest {
    @Test public void testAppHasAGreeting() {
        CliApp classUnderTest = new CliApp();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}