package edu.group7.csc415.studentorganizer;

import java.util.ArrayList;

/**
 * Created by Matt on 11/29/2017.
 */

public class CalendarCollection {
    public String date = "";
    public String event_message = "";
    public static ArrayList<CalendarCollection> cal_events_collection;
    public CalendarCollection(String date, String event_message) {
        this.date = date;
        this.event_message = event_message;
    }
}
