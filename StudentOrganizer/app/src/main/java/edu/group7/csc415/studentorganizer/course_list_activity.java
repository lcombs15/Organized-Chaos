package edu.group7.csc415.studentorganizer;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import java.util.ArrayList;

/**
 * Created by Matt on 11/8/2017.
 */

public class course_list_activity extends Fragment {

    ArrayList<Course> data = new ArrayList<Course>();
    private DBHelper mydb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_course_main, container, false);

        //Gson gson = new Gson();

        mydb = new DBHelper(getActivity());
        // Create ListView of skills that will be populated with data from json file
        ListView list = (ListView) view.findViewById(R.id.courses_list_view);

        // Test poll data from database for fragment population
        int numRows = mydb.numberOfRows();
        for (int i = 1; i <= numRows; i++) {
            Cursor course = mydb.getCourse(i);
            if (course != null && course.getCount() > 0) {
                course.moveToFirst();
                String courseName = course.getString(course.getColumnIndex(DBHelper.COURSES_COLUMN_TITLE));
                int courseID = course.getInt(course.getColumnIndex(DBHelper.COURSES_COLUMN_ID));
                data.add(new Course(courseID, courseName));
                if (!course.isClosed()) {
                    course.close();
                }
            }
        }

        CourseAdapter adapter = new CourseAdapter(getActivity(), data);
        list.setAdapter(adapter);

        // OnItemClickListener attacked to ListView to detect when a skill item has been clicked
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // On item click, a fragment transaction occurs to transfer the data related to the skill listed at the click position
                // which will be used to populate the widgets of the skill details fragment.
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                course_page_shell nextFragment = new course_page_shell();
                // Create new bundle and fill it with name, proficiency, and description of skill
                Bundle info = new Bundle();
                info.putInt("courseTag", data.get(position).getID());
                info.putString("courseName", data.get(position).getName());
                nextFragment.setArguments(info);
                // Replace current fragment with the skill details fragment containing the information of the selected skill
                transaction.replace(R.id.activity_courses_layout, nextFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
