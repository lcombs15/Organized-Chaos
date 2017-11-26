package edu.group7.csc415.studentorganizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 11/22/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {
    public CourseAdapter(Context context, ArrayList<Course> courses) {
        super(context, 0, courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Retrieve our data from the skill at this position
        Course course = getItem(position);
        // Determine if this view has already been used, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list, parent, false);
        }
        // Search view for data
        TextView name = (TextView) convertView.findViewById(R.id.courseName);

        // Populate skill data into template view with the data object
        name.setText(course.getName());

        // Return view of skill so that it will show on the list
        return convertView;
    }


}
