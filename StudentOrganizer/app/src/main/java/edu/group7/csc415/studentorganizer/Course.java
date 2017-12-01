package edu.group7.csc415.studentorganizer;

/**
 * Created by Matt on 11/22/2017.
 */

public class Course {

    private String courseTag;
    private String courseName;
    private String courseLocation;
    private String courseStart;
    private String courseEnd;
    private String courseDays;

    public Course(String courseTag, String courseName, String courseLocation, String courseStart, String courseEnd, String courseDays) {
        this.courseTag = courseTag;
        this.courseName = courseName;
        this.courseLocation = courseLocation;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseDays = courseDays;
    }

    public String getTag() {
        return courseTag;
    }

    public String getName() {
        return courseName;
    }

    public String getLocation() {
        return courseLocation;
    }

    public String getStart() { return courseStart; }

    public String getEnd() { return courseEnd; }

    public String getDays() { return courseDays; }

}
