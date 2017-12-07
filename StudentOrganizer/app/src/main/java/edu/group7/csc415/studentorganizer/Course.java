package edu.group7.csc415.studentorganizer;

/**
 * Created by Matt on 11/22/2017.
 */

public class Course {

    private int courseID;
    private String courseName;

    public Course(int courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public int getID() { return courseID; }

    public String getName() {
        return courseName;
    }

}
