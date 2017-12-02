package edu.group7.csc415.studentorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Cards.Card;
import Cards.CardAdapter;

public class MainMenu extends AppCompatActivity{
    //define variables for the widgets
    private Spinner quickAcccessSpinner;


    //define SharedPreferences object
    private SharedPreferences savedValues;

    //define instance variables
    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;
    private DBHelper mydb;
    private static final String errorMsg = "error";
    private static final String firstEntryKey = "FirstEntries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set view and widgets
        setContentView(R.layout.main_menu_activity);
        setupSpinner();
        setupActionBar();

        //set up RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        cAdapter = new TaskCardOnClickAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        //set up database and data for cards
        mydb = new DBHelper(this);
        //savedValues.edit().putBoolean("FirstEntries", false).apply();
        //prepopulateDB();

        prepareCardData();

        //set up button to add item
        final Button circleAddButton = (Button) findViewById(R.id.addItemButton);
        circleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(i);
            } //end onClick
        }); //end new OnClickListener
    } //end onCreate

    @Override
    public void onResume() {
        super.onResume();

        //repopulate cards
        CardList.clear();
        prepareCardData();
        cAdapter.notifyDataSetChanged();
    } //end onResume

    private void setupActionBar(){
        //TODO
    }

    private void setupSpinner(){
        //getReferences to the widgets
        quickAcccessSpinner = (Spinner) findViewById(R.id.navigationSpinner);

        //get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //set up the listener and functionality
        quickAcccessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                final Intent intent;
                switch(item) {
                    case "My Feed":
                        intent = new Intent(MainMenu.this, courses_activity.class);
                        startActivity(intent);
                        break;
                    case "My Courses":
                        //intent = new Intent(MainMenu.this, courses_activity.class);
                        intent = new Intent(MainMenu.this, courses_activity.class);
                        startActivity(intent);
                        break;
                    case "My Calendar":
                        intent = new Intent(MainMenu.this, CalendarListViewActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //create array adapter for Spinner
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.quick_access_array,
                        R.layout.spinner_item);

        //set the layout for the drop-down list
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set the adapter for the spinner and initialize position
        quickAcccessSpinner.setAdapter(spinnerAdapter);
        quickAcccessSpinner.setSelection(0);
    }

    private void prepopulateDB() {
        boolean firstEntries = savedValues.getBoolean(firstEntryKey, false);
        if (firstEntries == false) {
            mydb.insertCourse("CSC 402");
            mydb.insertCourse("CSC 415");
            if (true){//mydb.insertActivity("Text Lucas", "If this worked, text Lucas that SQLite is working.", 1)) {
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();
            }
            savedValues.edit().putBoolean(firstEntryKey, true).apply();
        }
        else {
            savedValues.edit().putBoolean(firstEntryKey, true).apply();
        }
    } //end prepopulateDB

    private void prepareCardData(){
        int numRows = mydb.numberOfRows();

        for(int i = 1; i <= 100; i++){
            Card c;
            if (i <= numRows) {
                Cursor result = mydb.getActivity(i);
                if (result != null && result.getCount()>0) {
                    result.moveToFirst();

                    String title = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE));
                    String desc = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION));
                    if (!result.isClosed()) {
                        result.close();
                    }
                    c = new Card(title, desc, new Date(), null);
                }
                else {
                    numRows += 1;
                    c = new Card("Empty ID", "This entry was deleted. Nothing here.", new Date(), null);
                }
            }
            else {
                c = new Card("Card #" + i,"Card description goes here!",new Date(),null);
            }
            CardList.add(c);
        } //end for
    } //end prepareCardData



    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TaskCardAdapter extends CardAdapter{
        /*
            To prevent code duplication the CardAdapter class is abstract. To
            change the onClick method of the cards, make another inner class like this one,
            implement a constructor and override the onBindViewHolder
        */

        // TaskCardAdapter constructor
        public TaskCardAdapter(List<Card> CardsList) {
            super(CardsList);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
        //Must be final for onClick to access
            final Card c = super.CardsList.get(position);

            //Bind data to Card layout
            holder.title.setText(c.getTitle());
            holder.description.setText(c.getDescription());

            // TODO fix this to handle null
            holder.icon.setImageResource(R.mipmap.ic_launcher_round);

            //Handle null string in DATE_FORMAT
            try {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm");
                holder.dueDate.setText(DATE_FORMAT.format(c.getDueDate()).toString());
            }
            catch (Exception e){
                holder.dueDate.setText(errorMsg);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private class TaskCardOnClickAdapter extends TaskCardAdapter{

        public TaskCardOnClickAdapter(List<Card> CardsList) {
            super(CardsList);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            //Needs to be final if onClick is going to use it
            final Card c = super.CardsList.get(position);

            //Bind all fields in Card XML to data
            holder.title.setText(c.getTitle());
            holder.description.setText(c.getDescription());

            // TODO fix this to handle null
            holder.icon.setImageResource(R.mipmap.ic_launcher_round);

            //Don't error out if DATE_FORMAT is passed a null string
            try {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm");
                holder.dueDate.setText(DATE_FORMAT.format(c.getDueDate()).toString());
            }catch (Exception e){
                holder.dueDate.setText("error");
            }

            //The almighty onClick for a Card
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"Changed OnClick",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainMenu.this, AddItemActivity.class);
                    intent.putExtra("id", position + 1);
                    startActivity(intent);
                }
            }); //end OnClickListener
        } //end setOnCLickListener
    } //end TaskCardAdapter class
} //end MainMenu
