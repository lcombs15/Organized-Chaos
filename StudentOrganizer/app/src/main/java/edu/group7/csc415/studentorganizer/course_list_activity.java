package edu.group7.csc415.studentorganizer;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Fragment;
import android.app.FragmentTransaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 11/8/2017.
 */

public class course_list_activity extends Fragment { //AppCompatActivity implements View.OnClickListener {

    //TextView courseText0;
    //TextView courseText1;
    //TextView courseText2;
    //TextView courseText3;
    //TextView courseText4;
    ArrayList<Course> data = new ArrayList<Course>();

    @Override
    public void onCreate(Bundle savedInstanceState) { //protected
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_course_main);

        //courseText0 = (TextView) findViewById(R.id.Course0);
        //courseText1 = (TextView) findViewById(R.id.Course1);
        //courseText2 = (TextView) findViewById(R.id.Course2);
        //courseText3 = (TextView) findViewById(R.id.Course3);
        //courseText4 = (TextView) findViewById(R.id.Course4);

        //courseText0.setOnClickListener(this);
        //courseText1.setOnClickListener(this);
        //courseText2.setOnClickListener(this);
        //courseText3.setOnClickListener(this);
        //courseText4.setOnClickListener(this);

    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ContextThemeWrapper derived from original activity context with specific theme
        //final Context themeWrapper = new ContextThemeWrapper(getActivity(), skills_activity.appTheme);
        // Clone LayoutInflator referencing themeWrapper
        //LayoutInflater newInflater = inflater.cloneInContext(themeWrapper);
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_course_main, container, false);
        //View view = newInflater.inflate(R.layout.fragment_skills, container, false);

        Gson gson = new Gson();

        // Create ListView of skills that will be populated with data from json file
        ListView list = (ListView) view.findViewById(R.id.courses_list_view);
        // Load json file data using Gson library
        data = gson.fromJson(loadGSON(), new TypeToken<List<Course>>(){}.getType());
        //Create skills adapter to help populate ListView with skills
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
                info.putString("courseTag", data.get(position).getTag());
                info.putString("courseName", data.get(position).getName());
                info.putString("courseLocation", data.get(position).getLocation());
                info.putString("courseStart", data.get(position).getStart());
                info.putString("courseEnd", data.get(position).getEnd());
                info.putString("courseDays", data.get(position).getDays());
                nextFragment.setArguments(info);
                // Replace current fragment with the skill details fragment containing the information of the selected skill
                transaction.replace(R.id.activity_courses_layout, nextFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    //loadGSON() reads json file for information needed to populate skills list and skill details fragments
    private String loadGSON() {
        String json = null;
        AssetManager am = getContext().getAssets();

        try
        {
            // Read skills.json for necessary data
            InputStream is = am.open("courses.json");
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);
            is.close();
            json = new String(buff, "UTF-8");
        } catch (IOException e) {
            // If errors occur, print the stack trace
            e.printStackTrace();
        }

        // Return the final string of data
        return json;
    }

}
