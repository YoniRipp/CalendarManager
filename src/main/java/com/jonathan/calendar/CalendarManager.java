package com.jonathan.calendar;

import com.jonathan.events.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CalendarManager {
    private Calendar calendar;
    private Scanner scanner;

    /**
     * Constructs a CalendarManager object.
     * Initializes the calendar and scanner for user input.
     */
    public CalendarManager() {
        this.calendar = new Calendar();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Safely parses an integer from a string.
     * Returns null if the parsing fails.
     *
     * @param input The string to parse as an integer.
     * @return The parsed integer or null if parsing fails.
     */
    private Integer safeParseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Starts the calendar manager and presents the menu options to the user.
     */
    public void start() {
        while (true) {
            System.out.println("\n1. Schedule an event");
            System.out.println("2. View all events");
            System.out.println("3. Remove an event");
            System.out.println("4. Modify an event");
            System.out.println("5. Search events of a specific date");
            System.out.println("6. Search for events by type");
            System.out.println("7. Search events by description");
            System.out.println("8. Search events by year");
            System.out.println("9. Search events by priority");
            System.out.println("10. Save events");
            System.out.println("11. Load events");
            System.out.println("12. Exit");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    scheduleEvent();
                    break;
                case "2":
                    viewAllEvents();
                    break;
                case "3":
                    removeEvent();
                    break;
                case "4":
                    modifyEvent();
                    break;
                case "5":
                    viewEventsOfSpecificDate();
                    break;
                case "6":
                    searchEventsByType();
                    break;
                case "7":
                    searchEventsByDescription();
                    break;
                case "8":
                    searchEventsByYear();
                    break;
                case "9":
                    searchEventsByPriority();
                    break;
                case "10":
                    calendar.saveCalendar();
                    break;
                case "11":
                    calendar.loadCalendar();
                    break;
                case "12":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Schedule an event by parsing user input and adding it to the calendar.
     */
    private void scheduleEvent() {
        Event event = parseEvent();
        if (event == null) {
            System.out.println("Invalid event details. Please try again.");
            return;
        }

        event.setID(calendar.getIDNum());
        calendar.scheduleEvent(event);
        System.out.println("Event scheduled successfully.");
    }

    /**
     * Remove an event from the calendar based on user input.
     */
    private void removeEvent() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
            return;
        }

        System.out.println("Enter the event ID to remove (Leave blank to cancel):");
        String line = scanner.nextLine().trim();
        if (line.isBlank()) {
            return;
        }

        Integer id = safeParseInt(line);
        if (id == null) {
            System.out.println("Invalid event ID. Please enter a valid number.");
            return;
        }

        Event eventToRemove = calendar.removeEvent(id);
        if (eventToRemove != null) {
            System.out.println("Event removed successfully: " + eventToRemove.getDescription());
        } else {
            System.out.println("Event not found.");
        }
    }

    /**
     * Search events by description based on user input.
     */
    private void searchEventsByDescription() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
            return;
        }

        System.out.println("Enter the keyword to search by description (Leave blank to cancel):");
        String keyword = scanner.nextLine().trim();
        if (keyword.isBlank()) {
            return;
        }

        List<Event> events = calendar.searchEventsByDescription(keyword);
        if (events.isEmpty()) {
            System.out.println("No events found with the given description keyword.");
        } else {
            System.out.println("Events found with the description keyword '" + keyword + "':");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * Search events by year based on user input.
     */
    private void searchEventsByYear() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
            return;
        }

        System.out.println("Enter the year (Leave blank to cancel):");
        String line = scanner.nextLine().trim();
        if (line.isBlank()) {
            return;
        }

        int year;
        try {
            year = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please enter a valid number.");
            return;
        }

        List<Event> events = calendar.searchEventsByYear(year);
        if (events.isEmpty()) {
            System.out.println("No events found in the given year.");
        } else {
            System.out.println("Events found in the year " + year + ":");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * Modify an event based on user input.
     */
    private void modifyEvent() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
        } else {
            System.out.println("Enter the event ID to modify (Leave blank to cancel):");
            String line = scanner.nextLine().trim();
            if (line.isBlank()) {
                return;
            }

            Integer id = safeParseInt(line);
            if (id == null) {
                System.out.println("Invalid event ID. Please enter a valid number.");
                return;
            }

            Event oldEvent = calendar.getEventByID(id);
            if (oldEvent == null) {
                System.out.println("Event not found.");
                return;
            }

            System.out.println("Enter the new details of the event (Leave blank to cancel):");
            Event newEventDetails = parseEvent();
            if (newEventDetails == null) {
                System.out.println("Invalid event details. Please try again.");
                return;
            }

            newEventDetails.setID(oldEvent.getID()); // Ensure ID remains the same
            calendar.updateEvent(newEventDetails);
            System.out.println("Event updated successfully.");
        }
    }

    /**
     * View all events in the calendar.
     */
    private void viewAllEvents() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
        } else {
            List<Event> events = calendar.viewAllEvents();
            System.out.println("All Events:");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * View events of a specific date based on user input.
     */
    private void viewEventsOfSpecificDate() {
        System.out.println("Enter the date (in the format dd-MM-yyyy) (Leave blank to cancel):");
        String dateString = scanner.nextLine().trim();
        if (dateString.isBlank()) {
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Please try again.");
            return;
        }

        List<Event> events = calendar.viewEventsOn(date);
        if (events.isEmpty()) {
            System.out.println("No events found on this date.");
        } else {
            System.out.println("Events on the date '" + dateString + "':");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * Search events by type based on user input.
     */
    private void searchEventsByType() {
        if (calendar.numEvents() == 0) {
            System.out.println("Empty calendar.");
            return;
        }

        System.out.println("Enter the event types separated by commas (MEETING,APPOINTMENT,TASK,WORK,SCHOOL,OTHER) (Leave blank to cancel):");
        String typeString = scanner.nextLine().toUpperCase();
        if (typeString.isBlank()) {
            return;
        }

        String[] types = typeString.split(",");
        Set<EventType> eventTypes = new HashSet<>();
        for (String type : types) {
            try {
                eventTypes.add(EventType.valueOf(type.trim()));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid event type: " + type);
            }
        }

        if (eventTypes.isEmpty()) {
            System.out.println("No valid event types entered.");
            return;
        }

        List<Event> events = calendar.searchEventsByType(eventTypes);
        if (events.isEmpty()) {
            System.out.println("No events of the given types found.");
        } else {
            System.out.println("Events of the given types:");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * Parses user input to create an Event object.
     *
     * @return The parsed Event object or null if parsing fails.
     */
    private Event parseEvent() {
        System.out.println("Description:");
        String description = scanner.nextLine().trim();
        if (description.isBlank()) {
            return null;
        }

        EventType type;
        while (true) {
            System.out.println("Type (MEETING, APPOINTMENT, WORK, SCHOOL, TASK, OTHER):");
            String typeString = scanner.nextLine().trim();
            if (typeString.isBlank()) {
                return null;
            }
            try {
                type = EventType.valueOf(typeString.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid event type. Please try again.");
            }
        }

        LocalDate date;
        while (true) {
            System.out.println("Date (in the format dd-MM-yyyy):");
            String dateString = scanner.nextLine().trim();
            if (dateString.isBlank()) {
                return null;
            }
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please try again.");
            }
        }

        LocalTime time;
        while (true) {
            System.out.println("Time (in the format HH:mm):");
            String timeString = scanner.nextLine().trim();
            if (timeString.isBlank()) {
                return null;
            }
            try {
                time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Please try again.");
            }
        }

        EventPrio prio;
        while (true) {
            System.out.println("Priority (HIGH, MID, LOW):");
            String prioString = scanner.nextLine().trim();
            if (prioString.isBlank()) {
                return null;
            }
            try {
                prio = EventPrio.valueOf(prioString.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid event type. Please try again.");
            }
        }

        LocalDateTime dateTime = LocalDateTime.of(date, time);
        calendar.nextID();
        return new Event(description, type, dateTime, prio);
    }

    /**
     * Search events by priority and display them in sorted order.
     */
    private void searchEventsByPriority() {
        if (calendar.numEvents() == 0) {
            System.out.println("No events found.");
            return;
        }

        List<Event> events = calendar.viewAllEvents();
        // Sort events by priority
        events.sort((e1, e2) -> e1.getPriority().compareTo(e2.getPriority()));

        System.out.println("Events sorted by priority:");
        for (Event event : events) {
            System.out.println(event);
        }
    }

    public void setEvents(List<Event> demoEvents) {
    }
}
