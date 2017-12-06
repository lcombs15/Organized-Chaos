package edu.group7.csc415.studentorganizer;

/**
 * Created by Matt on 11/22/2017.
 */

public class Course {

    private int courseID;
    private String courseName;
    //private String courseLocation;
    //private String courseStart;
    //private String courseEnd;
    //private String courseDays;

    public Course(int courseID, String courseName) { //String courseTag, String courseName, String courseLocation, String courseStart, String courseEnd, String courseDays
        this.courseID = courseID;
        this.courseName = courseName;
        //this.courseLocation = courseLocation;
        //this.courseStart = courseStart;
        //this.courseEnd = courseEnd;
        //this.courseDays = courseDays;
    }

    public int getID() { return courseID; }

    public String getName() {
        return courseName;
    }

    //public String getLocation() { return courseLocation; }

    //public String getStart() { return courseStart; }

    //public String getEnd() { return courseEnd; }

    //public String getDays() { return courseDays; }

}
