package com.jonathan.calendar;

import com.jonathan.events.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Calendar {
    private Map<Integer, Event> events;
    private Scanner scanner;
    private int curIDNum = 0;

    /**
     * Constructs a Calendar object.
     * Initializes the events map and the scanner for user input.
     */
    public Calendar() {
        this.events = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Retrieves the ID of the last added event.
     *
     * @return The ID of the last added event.
     */
    public int getIDNum() {
        return curIDNum;
    }

    /**
     * Increments the current ID number.
     * Used to assign a unique ID to each new event.
     */
    void nextID(){
        curIDNum++;
    }

    /**
     * Retrieves the number of events in the calendar.
     *
     * @return The number of events in the calendar.
     */
    public int numEvents(){
        return events.size();
    }

    /**
     * Schedules an event in the calendar.
     *
     * @param event The event to be scheduled.
     */
    public void scheduleEvent(Event event) {
        events.put(event.getID(), event);
    }
    
    /**
     * Removes an event from the calendar.
     *
     * @param id The ID of the event to be removed.
     * @return The removed event.
     */
    public Event removeEvent(int id) {
        return events.remove(id);
    }

    /**
     * Updates the details of an event in the calendar.
     *
     * @param newEventDetails The updated event details.
     */
    public void updateEvent(Event newEventDetails) {
        events.put(newEventDetails.getID(), newEventDetails);
    }

    /**
     * Retrieves an event from the calendar by its ID.
     *
     * @param id The ID of the event to retrieve.
     * @return The event corresponding to the ID, or null if not found.
     */
    public Event getEventByID(int id) {
        return events.get(id);
    }

    /**
     * Retrieves a list of all events in the calendar.
     * Returns a list of all events in the calendar, sorted by date and time.
     *
     * @return A list of all events in the calendar, sorted by date and time.
     */
    public List<Event> viewAllEvents() {
        return events.values().stream()
                .sorted(Comparator.comparing(Event::getDateTime))
                .collect(Collectors.toList());
    }


    /**
     * Saves the calendar as a file to be accessed later.
     * Prompts the user to provide a filename for the calendar.
     */
    public void saveCalendar() {
        System.out.println("Choose calendar name:");
        String filename = scanner.nextLine();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/jonathan/calendars/" + filename + ".cal"))) {
            oos.writeObject(events);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a calendar from a file.
     * Prompts the user to enter the filename of the calendar to load.
     * If the file is found and loaded successfully, it updates the events map with the loaded data.
     */
    public void loadCalendar() {
        boolean loaded = false;
        boolean stopLoading = false;
        while (!loaded && !stopLoading) {
            System.out.println("Which calendar to load? (Leave blank to cancel):");
            String filename = scanner.nextLine();
            if (filename.equalsIgnoreCase("")) {
                stopLoading = true;
            } else {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/jonathan/calendars/" + filename + ".cal"))) {
                    events = (Map<Integer, Event>) ois.readObject();
                    loaded = true;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found. Please try again.");
                } catch (Exception e) {
                    System.out.println("Error occurred while loading events. Please try again.");
                }
            }
        }
    }

    /**
     * Searches for events by a keyword in their descriptions.
     * Returns a list of events matching the keyword, sorted by time.
     *
     * @param keyword The keyword to search for in event descriptions.
     * @return A list of events matching the keyword, sorted by time.
     */
    public List<Event> searchEventsByDescription(String keyword) {
        return events.values().stream()
                .filter(event -> event.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .sorted(Comparator.comparing(event -> event.getDateTime().toLocalTime()))
                .collect(Collectors.toList());
    }

    
    /**
     * Searches for events by a specific date.
     * Returns a list of events matching the specified date, sorted by time.
     *
     * @param date The date to search for in event timestamps.
     * @return A list of events matching the specified date, sorted by time.
     */
    public List<Event> searchEventsByDay(LocalDate date) {
        return events.values().stream()
                .filter(event -> event.getDateTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(event -> event.getDateTime().toLocalTime()))
                .collect(Collectors.toList());
    }


    /**
     * Searches for events by a specific year.
     * Returns a list of events matching the specified year, sorted by year.
     *
     * @param year The year to search for in event timestamps.
     * @return A list of events matching the specified year, sorted by year.
     */
    public List<Event> searchEventsByYear(int year) {
        return events.values().stream()
                .filter(event -> event.getDateTime().getYear() == year)
                .sorted(Comparator.comparing(event -> event.getDateTime().getYear()))
                .collect(Collectors.toList());
    }
     /**
     * Retrieves a list of events on a specific date.
     * Returns a list of events on the specified date, sorted by time.
     *
     * @param date The date to filter events.
     * @return A list of events on the specified date, sorted by time.
     */
    public List<Event> viewEventsOn(LocalDate date) {
        return events.values().stream()
                .filter(event -> event.getDateTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(event -> event.getDateTime().toLocalTime()))
                .collect(Collectors.toList());
    }
    /**
     * Searches for events by their types.
     * Returns a list of events matching the specified types, sorted by time.
     *
     * @param types The set of event types to search for.
     * @return A list of events matching the specified types, sorted by time.
     */
    public List<Event> searchEventsByType(Set<EventType> types) {
        List<Event> matchingEvents = new ArrayList<>();
        for(EventType type : types) {
            matchingEvents.addAll(
                events.values().stream()
                    .filter(event -> event.getType() == type)
                    .sorted(Comparator.comparing(event -> event.getDateTime().toLocalTime()))
                    .collect(Collectors.toList())
            );
        }
        return matchingEvents;
    }
}
