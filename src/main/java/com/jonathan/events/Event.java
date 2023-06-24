package com.jonathan.events;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event class representing a specific event.
 * It stores a description, the type of the event, 
 * the date and time of the event, an ID for easier data manipulation, 
 * and the priority of the event.
 */
public class Event implements Serializable{
    private String description;
    private EventType type;
    private LocalDateTime dateTime;
    private int ID;
    private EventPrio prio;

    /**
     * Constructs an Event object with the given parameters.
     *
     * @param description Description of the event.
     * @param type Type of the event.
     * @param dateTime Date and time of the event.
     * @param prio Priority of the event.
     */
    public Event(String description, EventType type, LocalDateTime dateTime, EventPrio prio) {
        this.description = description;
        this.type = type;
        this.dateTime = dateTime;
        this.prio = prio;
    }

    /**
     * Retrieves the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the type of the event.
     *
     * @return The type of the event.
     */
    public EventType getType() {
        return type;
    }
    
    /**
     * Sets the type of the event.
     *
     * @param type The type of the event.
     */
    public void setType(EventType type){
        this.type = type;
    }

    /**
     * Retrieves the priority of the event.
     *
     * @return The priority of the event.
     */
    public EventPrio getPriority() {
        return this.prio;
    }
    
    /**
     * Sets the priority of the event.
     *
     * @param prio The priority of the event.
     */
    public void setPriority(EventPrio prio) {
        this.prio = prio;
    }

    /**
     * Retrieves the date and time of the event.
     *
     * @return The date and time of the event.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    /**
     * Sets the date and time of the event.
     *
     * @param dateTime The date and time of the event.
     */
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the ID of the event.
     *
     * @return The ID of the event.
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the ID of the event.
     *
     * @param id The ID of the event.
     */
    public void setID(int id) {
        this.ID = id;
    }

    /**
     * Returns a string representation of the Event object.
     *
     * @return A string representation of the Event object.
     */
    @Override
    public String toString() {
        String date = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        return "Event{ " +
                "id = " + ID +
                ", description = '" + description + '\'' +
                ", type = " + type +
                ", prio = " + prio +
                ", date = " + date +
                ", time = " + time +
                " }";
    }
}
