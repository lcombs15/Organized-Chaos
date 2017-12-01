package edu.group7.csc415.studentorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Justin on 11/4/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentOrganizer.db";
    public static final int DB_VERSION = 20171230_01; //Year(4) Month(2) Day(2) _ Update Number of day(2)

    public static final String ACTIVITIES_TABLE_NAME = "activities";
    public static final String ACTIVITIES_COLUMN_ID = "id";
    public static final String ACTIVITIES_COLUMN_TITLE = "title";
    public static final String ACTIVITIES_COLUMN_DESCRIPTION = "description";
    public static final String ACTIVITIES_COLUMN_DATE = "date";
    public static final String ACTIVITIES_COLUMN_COURSE_ID = "courseid";

    public static final String COURSES_TABLE_NAME = "courses";
    public static final String COURSES_COLUMN_ID = "id";
    public static final String COURSES_COLUMN_TITLE = "title";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + COURSES_TABLE_NAME + " (" +
                COURSES_COLUMN_ID + " integer primary key autoincrement, " +
                COURSES_COLUMN_TITLE + " text" +
                ");");
        db.execSQL("create table " + ACTIVITIES_TABLE_NAME + " (" +
                ACTIVITIES_COLUMN_ID + " integer primary key autoincrement, " +
                ACTIVITIES_COLUMN_TITLE + " text, " +
                ACTIVITIES_COLUMN_DESCRIPTION + " text, " +
                ACTIVITIES_COLUMN_DATE + " text, " +
                ACTIVITIES_COLUMN_COURSE_ID + " integer " +
                //"FOREIGN KEY (" + ACTIVITIES_COLUMN_COURSE_ID + ") REFERENCES " + COURSES_TABLE_NAME + "(" + COURSES_COLUMN_ID + ")" +
                ");");
        db.execSQL("insert into " + COURSES_TABLE_NAME + "(" + COURSES_COLUMN_ID + "," + COURSES_COLUMN_TITLE + ") values(1,'None');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertActivity(String title, String description, String date, int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITIES_COLUMN_TITLE, title);
        contentValues.put(ACTIVITIES_COLUMN_DESCRIPTION, description);
        contentValues.put(ACTIVITIES_COLUMN_DATE, date);
        contentValues.put(ACTIVITIES_COLUMN_COURSE_ID, courseID);
        db.insert(ACTIVITIES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertCourse(String title) {
        //Course names should be unique, so we check to make sure that a course with the same name doesn't exist.
        List<String> courses = getAllCourses();
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).equals(title)) {
                //If matches, don't let it record.
                return false;
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSES_COLUMN_TITLE, title);
        db.insert(COURSES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateActivity(int id, String title, String description, String date, int courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITIES_COLUMN_TITLE, title);
        contentValues.put(ACTIVITIES_COLUMN_DESCRIPTION, description);
        contentValues.put(ACTIVITIES_COLUMN_DATE, date);
        contentValues.put(ACTIVITIES_COLUMN_COURSE_ID, courseID);
        db.update(ACTIVITIES_TABLE_NAME, contentValues, ACTIVITIES_COLUMN_ID + " = ? ", new String[] {Integer.toString(id) });
        return true;
    }

    public int deleteActivity (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ACTIVITIES_TABLE_NAME, ACTIVITIES_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
    }

    public Cursor getActivity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String idString = Integer.toString(id);
        Cursor result = db.rawQuery("SELECT * FROM " + ACTIVITIES_TABLE_NAME + " WHERE " + ACTIVITIES_COLUMN_ID + "="+idString, null);
        //query(table, columns, where, whereArgs, groupBy, having, orderBy) -- Better method. Does its own cleaning to prevent SQL injects.
        //Cursor result = db.rawQuery("SELECT * FROM " + ACTIVITIES_TABLE_NAME + " WHERE id="+idString, null);
        return result;
    }

    public Cursor getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String idString = Integer.toString(id);
        Cursor result = db.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME + " WHERE " + COURSES_COLUMN_ID + "="+idString, null);
        return result;
    }

    public int getCourseID(String cname) {
        int courseID = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME + " WHERE " + COURSES_COLUMN_TITLE + "=" + cname, null);
        if (result != null && result.getCount()>0) {
            result.moveToFirst();
            courseID = result.getInt(result.getColumnIndex(COURSES_COLUMN_ID));
        }
        return courseID;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ACTIVITIES_TABLE_NAME);
        return numRows;
    }

    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + COURSES_TABLE_NAME, null);

        //loop through all and add to List
        if (cursor.moveToFirst()) {
            do {
                courses.add(cursor.getString(cursor.getColumnIndex(COURSES_COLUMN_TITLE)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return courses;
    }
}
