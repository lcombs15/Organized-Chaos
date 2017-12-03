package edu.group7.csc415.studentorganizer;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.group7.csc415.studentorganizer.CalendarAdapter;
import edu.group7.csc415.studentorganizer.CalendarCollection;

/**
 * Created by Matt on 11/29/2017.
 */

public class CalendarActivity extends AppCompatActivity {
    public GregorianCalendar cal_monthView, cal_monthView_dupe;
    private CalendarAdapter cal_adapter;
    private TextView cal_month_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //CalendarCollection.cal_events_collection = new ArrayList<CalendarCollection>();
        CalendarCollection.cal_events_collection.add(new CalendarCollection("2017-12-6", "Test", "Test Title", "Here's a test event!"));

        // Instantiate Gregorian Calendar and adapter
        cal_monthView = (GregorianCalendar) GregorianCalendar.getInstance();
        cal_monthView_dupe = (GregorianCalendar) cal_monthView.clone();
        cal_adapter = new CalendarAdapter(this, cal_monthView, CalendarCollection.cal_events_collection);

        // Populate calendar TextView
        cal_month_textView = (TextView) findViewById(R.id.cal_month_textView);
        cal_month_textView.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_monthView));

        ImageButton previous = (ImageButton) findViewById(R.id.iButton_prev);
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ImageButton next = (ImageButton) findViewById(R.id.iButton_next);
        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextMonth();
                refreshCalendar();
            }
        });

        // Now set up GridView to populate create and populate the calendar
        GridView grid = (GridView) findViewById(R.id.gridview_calendar);
        grid.setAdapter(cal_adapter);
        grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CalendarAdapter) parent.getAdapter()).setSelected(view, position);
                String selectedDate = CalendarAdapter.day_string.get(position);
                String[] timeDiff = selectedDate.split("-");
                String gridString = timeDiff[2].replaceFirst("^0*","");
                int gridVal = Integer.parseInt(gridString);

                // Check if we need previous month or next month
                if ((gridVal > 10) && (position < 8)) {
                    setPreviousMonth();
                    refreshCalendar();
                }
                else if ((gridVal < 7) && (position > 28)) {
                    setNextMonth();
                    refreshCalendar();
                }

                ((CalendarAdapter) parent.getAdapter()).setSelected(view, position);
                ((CalendarAdapter) parent.getAdapter()).getPositionList(selectedDate, CalendarActivity.this);
            }
        });

    }

    protected void setNextMonth() {
        if (cal_monthView.get(GregorianCalendar.MONTH) == cal_monthView.getActualMaximum(GregorianCalendar.MONTH)) {
            cal_monthView.set((cal_monthView.get(GregorianCalendar.YEAR) + 1), cal_monthView.getActualMinimum(GregorianCalendar.MONTH), 1);
        }
        else {
            cal_monthView.set(GregorianCalendar.MONTH, cal_monthView.get(GregorianCalendar.MONTH) + 1);
        }
    }

    protected void setPreviousMonth() {
        if (cal_monthView.get(GregorianCalendar.MONTH) == cal_monthView.getActualMinimum(GregorianCalendar.MONTH)) {
            cal_monthView.set((cal_monthView.get(GregorianCalendar.YEAR) - 1), cal_monthView.getActualMaximum(GregorianCalendar.MONTH), 1);
        }
        else {
            cal_monthView.set(GregorianCalendar.MONTH, cal_monthView.get(GregorianCalendar.MONTH) - 1);
        }
    }

    public void refreshCalendar() {
        cal_adapter.refreshDays();
        cal_adapter.notifyDataSetChanged();
        cal_month_textView.setText(android.text.format.DateFormat.format("MMMM yyyy", cal_monthView));
    }

}
