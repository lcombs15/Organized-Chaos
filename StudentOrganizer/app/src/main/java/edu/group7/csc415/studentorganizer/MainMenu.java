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
import android.widget.Button;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Cards.Card;
import Cards.CardAdapter;

public class MainMenu extends AppCompatActivity{

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

        // Now populate all activities into CalendarAdapter Collections so that events will show on our calendar
        CalendarCollection.cal_events_collection = new ArrayList<CalendarCollection>();
        populateCalendar();

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

        //repopulate date collections
        //CalendarCollection.cal_events_collection.clear();
        populateCalendar();

        //repopulate cards
        CardList.clear();
        prepareCardData();
        cAdapter.notifyDataSetChanged();
    } //end onResume

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Setup toolbar for Calendar & courses
        getMenuInflater().inflate(R.menu.navigation_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Intent intent;
        switch (item.getItemId()) {
            case R.id.action_my_courses:
                intent = new Intent(MainMenu.this, courses_activity.class);
                startActivity(intent);
                break;
            case R.id.action_my_calendar:
                intent = new Intent(MainMenu.this, CalendarActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void populateCalendar() {
        int numRows = mydb.numberOfRows();

        for (int i = 1; i <= numRows; i++) {
            Cursor result = mydb.getActivity(i);
            if (result != null && result.getCount() > 0) {
                result.moveToFirst();
                String courseID = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_COURSE_ID));
                String title = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE));
                String desc = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION));
                String date = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DATE));
                if (!result.isClosed()) {
                    result.close();
                }
                CalendarCollection.cal_events_collection.add(new CalendarCollection(date, courseID, title, desc));
            }
        }
    }

    private void prepareCardData(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int numRows = mydb.numberOfRows();

        for(int i = 1; i <= numRows; i++){
            Card c;
            Cursor result = mydb.getActivity(i);
            if (result != null && result.getCount()>0) {
                result.moveToFirst();

                int id = result.getInt(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_ID));
                String title = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE));
                String desc = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION));
                String dateStr = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DATE));
                if (!result.isClosed()) {
                    result.close();
                }
                Date date = new Date();
                try {
                    date = df.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c = new Card(id, title, desc, date); // new Date()
                CardList.add(c);
            }
            else {
                numRows += 1;
                //c = new Card("Empty ID", "This entry was deleted. Nothing here.", new Date(), null);
            }
        } //end for

        //Handle no data (new user)
        if(numRows < 1){
            Card helpCard = new Card("Welcome!");
            helpCard.setDescription("Click the plus button to get started!");
            helpCard.setTaskID(0);
            helpCard.setDueDate(new Date());
            CardList.add(helpCard);
        }
    } //end prepareCardData

    private class TaskCardOnClickAdapter extends CardAdapter{

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
            holder.taskID.setText(Integer.toString(c.getTaskID()));

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
                    Intent intent = new Intent(MainMenu.this, AddItemActivity.class);
                    intent.putExtra("id", c.getTaskID());
                    startActivity(intent);
                }
            }); //end OnClickListener
        } //end setOnCLickListener
    } //end TaskCardAdapter class
} //end MainMenu
