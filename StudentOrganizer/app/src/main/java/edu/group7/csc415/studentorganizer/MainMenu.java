package edu.group7.csc415.studentorganizer;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import layout.templates.Page.*; //We're at some point or another going to need everything in here

public class MainMenu extends AppCompatActivity{
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

    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        setupSpinner();
        setupActionBar();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        cAdapter = new CardAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        mydb = new DBHelper(this);
        boolean firstEntries = savedValues.getBoolean("FirstEntries", false);
        if (firstEntries == false) {
            mydb.insertActivity("Android Final", "Finish the app for the final project in Android Development.");
            mydb.insertActivity("Register Classes", "Register classes for next semester.");
            if (mydb.insertActivity("Text Lucas", "If this worked, text Lucas that SQLite is working.")) {
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();
            }

            savedValues.edit().putBoolean("FirstEntries", true).commit();
        }
        else {
            savedValues.edit().putBoolean("FirstEntries", true).commit();
        }

        prepareCardData();

        final Button circleAddButton = (Button) findViewById(R.id.addItemButton);
        circleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(i);
            }
        });
    }

    private void prepareCardData(){

        int numRows = mydb.numberOfRows();
        for(int i = 1; i <= 100; i++){
            Card c;
            if (i <= numRows) {
                Cursor result = mydb.getData(i);
                result.moveToFirst();

                String title = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE));
                String desc = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION));
                if (!result.isClosed()) {
                    result.close();
                }
                c = new Card(title, desc, new Date(), null);
            }
            else {
                c = new Card("Card #" + i,"Card description goes here!",new Date(),null);
            }
            //Card c = new Card("Card #" + i,"Card description goes here!",new Date(),null);
            CardList.add(c);
        }
    }

    private void setupActionBar(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* TODO
        Intent menu = new Intent(getApplicationContext(),item.getActionView().getClass());
        startActivity(menu);
        */
        return true;
    }

    private void setupSpinner(){
        //getReferences to the widgets
        quickAcccessSpinner = (Spinner) findViewById(R.id.navigationSpinner);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //set the Listeners
        quickAcccessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //create array adapter for Spinner
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.quick_access_array,
                        android.R.layout.simple_spinner_item);

        //set the layout for the drop-down list
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set the adapter for the spinner and initialize position
        quickAcccessSpinner.setAdapter(spinnerAdapter);
        quickAcccessSpinner.setSelection(0);
    }
}
