package edu.group7.csc415.studentorganizer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Spinner typeSpinner, courseValueSpinner;
    private EditText name, time, descript;
    private TextView courseLabel;
    private Button enterButton, clearButton;

    private DBHelper mydb;
    private int selectedID = 0;
    private static final String idKey = "id";
    private static final String selectTypeText = "Select Type";
    private static final String courseText = "Course";
    private static final String taskText = "Task";
    private static final String successMsg = "Success";
    private static final String failedMsg = "Failed";
    private static final String deleteSuccessMsg = "Deleted Successfully";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //assign variables to widgets
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        courseValueSpinner = (Spinner) findViewById(R.id.courseValueSpinner);
        name = (EditText) findViewById(R.id.nameValue);
        time = (EditText) findViewById(R.id.timeValue);
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

        loadCourseTitles();

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

    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enterButton:
                String nameText = name.getText().toString();
                String descriptionText = descript.getText().toString();
                if (!nameText.equals("")) {
                    if (selectedID == 0)
                    {
                        String typeToInsert = typeSpinner.getSelectedItem().toString();
                        //add new activity
                        if (typeToInsert.equals("Task"))
                        {
                            if (mydb.insertActivity(nameText, descriptionText, 1)) {
                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        //add new course
                        if (typeToInsert.equals("Course"))
                        {
                            if (mydb.insertCourse(nameText)) {
                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed. Duplicate name.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Please select a type from the dropdown.", Toast.LENGTH_SHORT).show();
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
                }
                else {
                    Toast.makeText(this, "Please enter a name into the field.", Toast.LENGTH_SHORT).show();
                } //end if name not entered
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
                courseValueSpinner.setVisibility(View.GONE);
                break;
            case courseText:
                courseLabel.setVisibility(View.GONE);
                courseValueSpinner.setVisibility(View.GONE);
                break;
            case taskText:
                courseLabel.setVisibility(View.VISIBLE);
                courseValueSpinner.setVisibility(View.VISIBLE);
                loadCourseTitles();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setDate(final Calendar calendar) {
        //final DateFormat dateFormat = DateFormat.getDateInstance("yyyy-MM-dd");
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        ((TextView) findViewById(R.id.selectedDate)).setText(dateFormat.format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }

    public void loadCourseTitles() {
        List<String> courses = mydb.getAllCourses();
        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseValueSpinner.setAdapter(coursesAdapter);
    }
} //end AddItemActivity class