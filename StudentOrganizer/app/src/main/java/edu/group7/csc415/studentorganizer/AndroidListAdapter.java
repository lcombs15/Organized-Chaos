package edu.group7.csc415.studentorganizer;

import java.util.ArrayList;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.group7.csc415.studentorganizer.R;
import edu.group7.csc415.studentorganizer.CalendarCollection;

/**
 * Created by Matt on 11/29/2017.
 */

public class AndroidListAdapter extends ArrayAdapter<CalendarCollection> {
    private final Context context;
    private final ArrayList<CalendarCollection> vals;
    private ViewHolder viewH;
    private final int resourceId;

    public AndroidListAdapter(Context context, int resourceId, ArrayList<CalendarCollection> vals) {
        super(context, resourceId, vals);
        this.context = context;
        this.vals = vals;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewH = new ViewHolder();
            viewH.tv_event = (TextView) convertView.findViewById(R.id.tv_event);
            viewH.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewH);
        }
        else {
            viewH = (ViewHolder) convertView.getTag();
        }
        CalendarCollection list_obj = vals.get(pos);
        viewH.tv_date.setText(list_obj.date);
        viewH.tv_date.setText(list_obj.event_message);
        return convertView;
    }

    public class ViewHolder {
        TextView tv_event;
        TextView tv_date;
    }
}