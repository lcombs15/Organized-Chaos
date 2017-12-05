package edu.group7.csc415.studentorganizer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Matt on 11/29/2017.
 */

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private Calendar month;
    public GregorianCalendar pub_month;
    public GregorianCalendar pub_month_max;
    private GregorianCalendar selectedDate;
    int fDay;
    int maxWeekNum;
    int maxP;
    int calMaxP;
    int monthLength;
    String itemVal, currentDate;
    DateFormat df;
    private ArrayList<String> items;
    public static List<String> day_string;
    private View previousView;
    public ArrayList<CalendarCollection> cal_events_collection;

    public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> cal_events_collection) {
        this.cal_events_collection = cal_events_collection;
        CalendarAdapter.day_string = new ArrayList<String>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        this.context = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        currentDate = df.format(selectedDate.getTime());
        refreshDays();
    }

    public int getCount() {
        return day_string.size();
    }

    public Object getItem(int pos) {
        return day_string.get(pos);
    }

    public long getItemId(int pos) {
        return 0;
    }

    public void setItems(ArrayList<String> items) {
        for (int i =0; i != items.size(); i++) {
            if (items.get(i).length() == 1) {
                items.set(i, "0" + items.get(i));
            }
        }
        this.items = items;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView dayView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.cal_item, null);
        }
        dayView = (TextView)v.findViewById(R.id.date);
        String[] timeDiff = day_string.get(pos).split("-");

        String gridVal = timeDiff[2].replaceFirst("^0*", "");
        if ((Integer.parseInt(gridVal) > 1) && (pos < fDay)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        }
        else if ((Integer.parseInt(gridVal) < 7) && (pos > 28)) {
            dayView.setTextColor(Color.GRAY);
            dayView.setClickable(false);
            dayView.setFocusable(false);
        }
        else {
            dayView.setTextColor(Color.WHITE);
        }

        // Check if current position matches current date of calendar
        if (day_string.get(pos).equals(currentDate)) {
            v.setBackgroundColor(v.getResources().getColor(R.color.themeColorLight,null));
        }
        else {
            v.setBackgroundColor(Color.parseColor("#343434"));
        }

        dayView.setText(gridVal);
        String date = day_string.get(pos);
        if (date.length() == 1) {
            date = "0" + date;
        }
        String monthString = "" + (month.get(GregorianCalendar.MONTH) + 1);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }

        setEventView(v, pos, dayView);
        return v;
    }

    public View setSelected(View view, int pos) {
        if (previousView != null) {
            previousView.setBackgroundColor(Color.parseColor("#343434"));
        }
        view.setBackgroundColor(view.getResources().getColor(R.color.themeColorLight,null));
        int length = day_string.size();
        if (length > pos) {
            if (day_string.get(pos).equals(currentDate)) {
                /* Good! */
            }
            else {
                previousView = view;
            }
        }

        return view;
    }

    public void refreshDays() {
        items.clear();
        day_string.clear();
        Locale.setDefault(Locale.US);
        pub_month = (GregorianCalendar) month.clone();
        fDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        maxWeekNum = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        monthLength = maxWeekNum * 7;
        maxP = getMaxP();
        calMaxP = maxP - (fDay - 1);
        pub_month_max = (GregorianCalendar) pub_month.clone();
        pub_month_max.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
        for (int i = 0; i < monthLength; i++) {
            itemVal = df.format(pub_month_max.getTime());
            pub_month_max.add(GregorianCalendar.DATE, 1);
            day_string.add(itemVal);
        }
    }

    private int getMaxP() {
        int maxP;
        if(month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pub_month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
        }
        else {
            pub_month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pub_month.getActualMaximum((GregorianCalendar.DAY_OF_MONTH));
        return maxP;
    }

    public void setEventView(View v, int pos, TextView text) {
        int length = CalendarCollection.cal_events_collection.size();
        for (int i = 0; i < length; i++) {
            CalendarCollection cal_inst = CalendarCollection.cal_events_collection.get(i);
            String date = cal_inst.date;
            int lengthDupe = day_string.size();
            if (lengthDupe > pos) {
                if (day_string.get(pos).equals(date)) {
                    v.setBackgroundColor(Color.parseColor("#343434"));
                    //v.setBackgroundResource(R.drawable.rounded_calendar_item);
                    text.setTextColor(Color.WHITE);
                }
            }
        }
    }

    public void getPositionList(String date, final Activity act) {
        int length = CalendarCollection.cal_events_collection.size();
        for (int i = 0; i < length; i++) {
            CalendarCollection cal_collection = CalendarCollection.cal_events_collection.get(i);
            String event_date = cal_collection.date;
            String event_title = cal_collection.title;
            String event_courseID = cal_collection.courseID;
            String event_message = cal_collection.event_message;
            if (date.equals(event_date)) {
                Toast.makeText(context, "You have an event on this date: " + event_date, Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Course: " + event_courseID + "\nEvent: " + event_title).setMessage("Date: " + event_date + "\nDescription: " + event_message).setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int x) {
                        //act.finish();
                    }
                }).show();
                break;
            }
            else {
                /* Nothing */
            }
        }
    }


}
