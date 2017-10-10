package edu.group7.csc415.studentorganizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.group7.csc415.studentorganizer.R;

public class course_page_shell extends AppCompatActivity implements OnClickListener{

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    Button editButton;
    Button task1;
    Button task2;
    Button task3;
    Button task4;
    EditText courseNameText;
    EditText startTimeText;
    EditText endTimeText;
    EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page_shell);

        spinner = (Spinner) findViewById(R.id.quick_access_spinner);
        //Construct ArrayAdapter referencing the string array for spinner options and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.navigation_array, android.R.layout.simple_spinner_item);
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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edit_course_name_button:
                courseNameText.setEnabled(true);
                startTimeText.setEnabled(true);
                endTimeText.setEnabled(true);
                locationText.setEnabled(true);
                break;
            case R.id.course_task1_button:
                // Should display pop-up window of more details on task with ability to modify the details, once we have this pop-up implemented
                break;
            case R.id.course_task2_button:
                // Should display pop-up window of more details on task with ability to modify the details, once we have this pop-up implemented
                break;
            case R.id.course_task3_button:
                // Should display pop-up window of more details on task with ability to modify the details, once we have this pop-up implemented
                break;
            case R.id.course_task4_button:
                // Should display pop-up window of more details on task with ability to modify the details, once we have this pop-up implemented
                break;
        }
    }
}
