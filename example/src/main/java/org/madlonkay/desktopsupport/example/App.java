/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.madlonkay.desktopsupport.example;

import org.madlonkay.desktopsupport.DesktopSupport;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        new DesktopSupport().addAboutHandler(null);
    }
}
