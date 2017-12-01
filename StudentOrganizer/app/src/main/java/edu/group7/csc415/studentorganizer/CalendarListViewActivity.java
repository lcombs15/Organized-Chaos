package edu.group7.csc415.studentorganizer;

import java.util.ArrayList;
import edu.group7.csc415.studentorganizer.R;
import edu.group7.csc415.studentorganizer.AndroidListAdapter;
import edu.group7.csc415.studentorganizer.CalendarCollection;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Matt on 11/29/2017.
 */

public class CalendarListViewActivity extends AppCompatActivity {
    private ListView cal_listView;
    private AndroidListAdapter cal_list_adapter;
    private Button cal_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_list_view);

        // Adding some stock events, later we should implement events via SQL database query
        CalendarCollection.cal_events_collection = new ArrayList<CalendarCollection>();
        CalendarCollection.cal_events_collection.add(new CalendarCollection("2017-11-29", "Here's a test event!"));

        cal_button = (Button) findViewById(R.id.cal_button);
        cal_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalendarListViewActivity.this, CalendarActivity.class));
            }
        });
    }

}
