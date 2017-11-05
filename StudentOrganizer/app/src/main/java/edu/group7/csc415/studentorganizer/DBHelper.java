package edu.group7.csc415.studentorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Justin on 11/4/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentOrganizer.db";
    public static final String ACTIVITIES_TABLE_NAME = "activities";
    public static final String ACTIVITIES_COLUMN_ID = "id";
    public static final String ACTIVITIES_COLUMN_TITLE = "title";
    public static final String ACTIVITIES_COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ACTIVITIES_TABLE_NAME +
                    "(id integer primary key, title text, description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ACTIVITIES_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertActivity(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITIES_COLUMN_TITLE, title);
        contentValues.put(ACTIVITIES_COLUMN_DESCRIPTION, description);
        db.insert(ACTIVITIES_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String idString = Integer.toString(id);
        Cursor result = db.rawQuery("SELECT * FROM " + ACTIVITIES_TABLE_NAME + " WHERE id="+idString, null);
        return result;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ACTIVITIES_TABLE_NAME);
        return numRows;
    }
}
