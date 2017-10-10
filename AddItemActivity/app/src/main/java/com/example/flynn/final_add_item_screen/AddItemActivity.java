package com.example.flynn.final_add_item_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner typeSpinner;
    private EditText name, time, courseValue, descript;
    private TextView courseLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        name = (EditText) findViewById(R.id.nameValue);
        time = (EditText) findViewById(R.id.timeValue);
        courseValue = (EditText) findViewById(R.id.courseValue);
        descript = (EditText) findViewById(R.id.descriptionValue);
        courseLabel = (TextView) findViewById(R.id.courseLabel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Object selected;
        selected = parent.getItemAtPosition(pos);
        switch (selected.toString())
        {
            case "Select Type":
                courseLabel.setVisibility(View.GONE);
                courseValue.setVisibility(View.GONE);
                break;
            case "Course":
                courseLabel.setVisibility(View.GONE);
                courseValue.setVisibility(View.GONE);
                break;
            case "Task":
                courseLabel.setVisibility(View.VISIBLE);
                courseValue.setVisibility(View.VISIBLE);
                break;
            case "Reminder":
                courseLabel.setVisibility(View.VISIBLE);
                courseValue.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}
