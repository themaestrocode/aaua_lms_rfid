package com.themaestrocode.aaualms.entity;

import java.sql.Timestamp;

public class Event {

    private int eventId;
    private String eventType;
    private String bookId;
    private String userLibraryId;
    private Timestamp eventDate;

    public Event(int eventId, String eventType, String bookId, String userLibraryId, Timestamp eventDate) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.bookId = bookId;
        this.userLibraryId = userLibraryId;
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserLibraryId() {
        return userLibraryId;
    }

    public void setUserLibraryId(String userLibraryId) {
        this.userLibraryId = userLibraryId;
    }

    public Timestamp getEventDate() {
        return eventDate;
    }

    public void setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
    }
}
