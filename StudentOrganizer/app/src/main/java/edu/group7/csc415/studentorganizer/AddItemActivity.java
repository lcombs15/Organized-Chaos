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
        enterButton = (Button) findViewById(R.id.enterButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        mydb = new DBHelper(this);
        enterButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


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
                        if (mydb.insertActivity(nameText, descriptionText))
                        {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
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
