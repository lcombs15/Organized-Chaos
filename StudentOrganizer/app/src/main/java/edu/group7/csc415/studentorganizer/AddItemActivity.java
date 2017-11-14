package edu.group7.csc415.studentorganizer;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner typeSpinner;
    private EditText name, time, courseValue, descript;
    private TextView courseLabel;
    private Button enterButton, clearButton;

    private DBHelper mydb;
    private int selectedID = 0;
    private static final String idKey = "id";
    private static final String selectTypeText = "Select Type";
    private static final String courseText = "Course";
    private static final String taskText = "Task";
    private static final String reminderText = "Reminder";
    private static final String successMsg = "Success";
    private static final String failedMsg = "Failed";
    private static final String deleteSuccessMsg = "Deleted Successfully";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //assign variables to widgets
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        name = (EditText) findViewById(R.id.nameValue);
        time = (EditText) findViewById(R.id.timeValue);
        courseValue = (EditText) findViewById(R.id.courseValue);
        descript = (EditText) findViewById(R.id.descriptionValue);
        courseLabel = (TextView) findViewById(R.id.courseLabel);
        enterButton = (Button) findViewById(R.id.enterButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        //set adapter for Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        //set up database helper and wire button listeners
        mydb = new DBHelper(this);
        enterButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        //check if an id was passed with intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idValue = extras.getInt(idKey);

            //If an id value is already assigned, then we are editing a value, not adding a value
            if (idValue>0) {
                //get item to be edited
                Cursor result = mydb.getActivity(idValue);
                result.moveToFirst();
                selectedID = idValue;

                //set new title and description
                name.setText(result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE)));
                descript.setText(result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION)));

                //close cursor
                if (!result.isClosed()) {
                    result.close();
                }

                //set text on buttons for commiting or cancelling change
                enterButton.setText(R.string.update);
                clearButton.setText(R.string.delete);
            }//end of editing value
        }//end if
    } //end onCreate

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterButton:
                String nameText = name.getText().toString();
                String descriptionText = descript.getText().toString();
                if (!nameText.equals("")) {
                    if (selectedID == 0)
                    {
                        //add new activity
                        if (mydb.insertActivity(nameText, descriptionText))
                        {
                            Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, failedMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (selectedID > 0)
                    {
                        //update activity
                        if (mydb.updateActivity(selectedID, nameText, descriptionText))
                        {
                            Toast.makeText(this, successMsg, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, failedMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } //end nameText not blank
            break; //end case enterButton
            case R.id.clearButton:
                if (selectedID == 0)
                {
                    name.setText("");
                    time.setText("");
                    descript.setText("");
                }
                else if (selectedID > 0)
                {
                    //delete activity
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setMessage(R.string.deleteItem).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mydb.deleteActivity(selectedID);
                            Toast.makeText(getApplicationContext(), deleteSuccessMsg, Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //User cancelled the delete
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }
                break; //end case clearButton
        } //end switch
    } //end onClickView

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Object selected;
        selected = parent.getItemAtPosition(pos);
        switch (selected.toString())
        {
            case selectTypeText:
                courseLabel.setVisibility(View.GONE);
                courseValue.setVisibility(View.GONE);
                break;
            case courseText:
                courseLabel.setVisibility(View.GONE);
                courseValue.setVisibility(View.GONE);
                break;
            case taskText:
                courseLabel.setVisibility(View.VISIBLE);
                courseValue.setVisibility(View.VISIBLE);
                break;
            case reminderText:
                courseLabel.setVisibility(View.VISIBLE);
                courseValue.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
} //end AddItemActivity class
