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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

        cAdapter = new TaskCardAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        mydb = new DBHelper(this);
        prepopulateDB();

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

    @Override
    public void onResume() {
        super.onResume();

        CardList.clear();
        prepareCardData();
    }

    private void prepopulateDB() {
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

            savedValues.edit().putBoolean("FirstEntries", true).apply();
        }
        else {
            savedValues.edit().putBoolean("FirstEntries", true).apply();
        }
    }

    private void prepareCardData(){

        int numRows = mydb.numberOfRows();
        for(int i = 1; i <= 100; i++){
            Card c;
            if (i <= numRows) {
                Cursor result = mydb.getActivity(i);
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

    /*
        To prevent code duplication I have made the CardAdapter class abstract.

        We will have to make another inner class, like this one, any time we want to change the onClick method of the cards
        You only need to have a constructor and override the onBindViewHolder
     */
    private class TaskCardAdapter extends CardAdapter{

        public TaskCardAdapter(List<Card> CardsList) {
            super(CardsList);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
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
                    Toast.makeText(getApplicationContext(),"TODO: Add On Click....." + c.getTitle().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
