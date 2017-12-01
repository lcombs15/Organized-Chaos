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

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner typeSpinner, courseValueSpinner;
    private EditText name, time, descript;
    private TextView courseLabel;
    private Button enterButton, clearButton;

    private DBHelper mydb;
    private int selectedID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        courseValueSpinner = (Spinner) findViewById(R.id.courseValueSpinner);
        name = (EditText) findViewById(R.id.nameValue);
        time = (EditText) findViewById(R.id.timeValue);
        descript = (EditText) findViewById(R.id.descriptionValue);
        courseLabel = (TextView) findViewById(R.id.courseLabel);
        enterButton = (Button) findViewById(R.id.enterButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        mydb = new DBHelper(this);
        enterButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        loadCourseTitles();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int idValue = extras.getInt("id");

            //If an id value is already assigned, then we are editing a value, not adding a value;
            if (idValue>0) {
                Cursor result = mydb.getActivity(idValue);
                result.moveToFirst();
                selectedID = idValue;

                name.setText(result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE)));
                descript.setText(result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION)));

                if (!result.isClosed()) {
                    result.close();
                }

                enterButton.setText(R.string.update);
                clearButton.setText(R.string.delete);
            }
        }
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
                        if (typeToInsert.equals("Task"))
                        {
                            if (mydb.insertActivity(nameText, descriptionText, 1)) {
                                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
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
                        if (mydb.updateActivity(selectedID, nameText, descriptionText))
                        {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Please enter a name into the field.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clearButton:
                if (selectedID == 0)
                {
                    name.setText("");
                    time.setText("");
                    descript.setText("");
                }
                else if (selectedID > 0)
                {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                    alertBuilder.setMessage(R.string.deleteItem).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mydb.deleteActivity(selectedID);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();

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
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Object selected;
        selected = parent.getItemAtPosition(pos);
        switch (selected.toString())
        {
            case "Select Type":
                courseLabel.setVisibility(View.GONE);
                courseValueSpinner.setVisibility(View.GONE);
                break;
            case "Course":
                courseLabel.setVisibility(View.GONE);
                courseValueSpinner.setVisibility(View.GONE);
                break;
            case "Task":
                courseLabel.setVisibility(View.VISIBLE);
                courseValueSpinner.setVisibility(View.VISIBLE);
                loadCourseTitles();
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loadCourseTitles() {
        List<String> courses = mydb.getAllCourses();
        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseValueSpinner.setAdapter(coursesAdapter);
    }
}
