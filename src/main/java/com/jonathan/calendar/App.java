package com.jonathan.calendar;

/**
 * The main class that starts the calendar application.
 * It creates an instance of CalendarManager and starts the application.
 */
public class App {
    public static void main(String[] args) {
        CalendarManager manager = new CalendarManager();
        manager.start();
    }
}
