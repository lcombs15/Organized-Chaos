package edu.group7.csc415.studentorganizer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import layout.templates.Page.Card;
import layout.templates.Page.CardAdapter;

/**
 * Created by Matt on 11/22/2017.
 */

public class course_page_shell extends Fragment {//AppCompatActivity {

    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;

    private String courseTag;
    private String courseName;
    private String courseLocation;
    private String courseStart;
    private String courseEnd;
    private String courseDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_course_page_shell);

        spinner = (Spinner) findViewById(R.id.quick_access_spinner);
        //Construct ArrayAdapter referencing the string array for spinner options and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.quick_access_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        courseNameText = (EditText) findViewById(R.id.course_label);
        startTimeText = (EditText) findViewById(R.id.course_start_time_label);
        endTimeText = (EditText) findViewById(R.id.course_end_time_label);
        locationText = (EditText) findViewById(R.id.course_location_label);

        editButton = (Button) findViewById(R.id.edit_course_name_button);
        task1 = (Button) findViewById(R.id.course_task1_button);
        task2 = (Button) findViewById(R.id.course_task2_button);
        task3 = (Button) findViewById(R.id.course_task3_button);
        task4 = (Button) findViewById(R.id.course_task4_button);

        courseNameText.setEnabled(false);
        startTimeText.setEnabled(false);
        endTimeText.setEnabled(false);
        locationText.setEnabled(false);

        editButton.setOnClickListener(this);
        task1.setOnClickListener(this);
        task2.setOnClickListener(this);
        task3.setOnClickListener(this);
        task4.setOnClickListener(this);
=======
        setHasOptionsMenu(true);

        //Load bundle containing information about the skill that should be displayed.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            courseTag = bundle.getString("courseTag", "DEFAULT");
            courseName = bundle.getString("courseName", "DEFAULT");
            courseLocation = bundle.getString("courseLocation", "DEFAULT");
            courseStart = bundle.getString("courseStart", "DEFAULT");
            courseEnd = bundle.getString("courseEnd", "DEFAULT");
            courseDays = bundle.getString("courseDays", "DEFAULT");
        }

    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ContextThemeWrapper derived from original activity context with specific theme
        //final Context themeWrapper = new ContextThemeWrapper(getActivity(), skills_activity.appTheme);
        // Clone LayoutInflator referencing themeWrapper
        //LayoutInflater newInflater = inflater.cloneInContext(themeWrapper);
        // inflate the layout for this fragment
        //View view = newInflater.inflate(R.layout.fragment_skill_details, container, false);
        View view = inflater.inflate(R.layout.activity_course_page, container, false);

        // Populate widgets with the data retrieved from bundle that is related to a specific skill
        TextView name = (TextView) view.findViewById(R.id.course_label);
        TextView location = (TextView) view.findViewById(R.id.course_location_label);
        TextView startTime = (TextView) view.findViewById(R.id.course_start_time_label);
        TextView endTime = (TextView) view.findViewById(R.id.course_end_time_label);
        name.setText(courseName);
        location.setText(courseLocation);
        startTime.setText(courseStart);
        endTime.setText(courseEnd);

        recyclerView = (RecyclerView) view.findViewById(R.id.course_tasks_recycleView);
        cAdapter = new CardAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        prepareCardData();

        return view;
>>>>>>> Matt-Devel
    }

    @Override
    public void onResume() {
        super.onResume();

        //repopulate cards
        CardList.clear();
        prepareCardData();
    } //end onResume

    private void prepareCardData(){

        for(int i = 1; i <= 100; i++){
            Card c = new Card("Card #" + i,"Card description goes here!",new Date(),null);
            CardList.add(c);
        }
    }

}
