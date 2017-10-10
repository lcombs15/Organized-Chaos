package com.csc515.edwards.studentorganizer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class HomePage extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //define variables for the widgets
    private Spinner quickAcccessSpinner;
    private LinearLayout buttonLayout;

    //define SharedPreferences object
    private SharedPreferences savedValues;

    //define instance variables
    private static final String defaultText = "Add classes, reminders, or tasks";
    private Button defaultButton1;
    private Button defaultButton2;
    private Spinner quickAccessSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //getReferences to the widgets
        defaultButton1 = (Button) findViewById(R.id.default_button_1);
        defaultButton2 = (Button) findViewById(R.id.default_button_2);
        quickAcccessSpinner = (Spinner) findViewById(R.id.quick_access_spinner);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //set the Listeners
        defaultButton1.setOnClickListener(this);
        defaultButton2.setOnClickListener(this);
        quickAcccessSpinner.setOnItemSelectedListener(this);

        //create array adapter for Spinner
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.quick_access_array,
                android.R.layout.simple_spinner_item);

        //set the layout for the drop-down list
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set the adapter for the spinner and initialize position
        quickAcccessSpinner.setAdapter(spinnerAdapter);
        quickAcccessSpinner.setSelection(0);

        //initialize buttons
        defaultButton1.setEnabled(true);
        defaultButton2.setEnabled(true);
        defaultButton1.setText(defaultText);
        defaultButton2.setText(defaultText);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume (){
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.default_button_1:
                Toast.makeText(this, "Default Button 1 clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.default_button_2:
                Toast.makeText(this, "Default Button 2 clicked", Toast.LENGTH_LONG).show();
                break;
        } //end switch
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // Insert Code
    }
}

