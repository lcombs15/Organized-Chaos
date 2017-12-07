package edu.group7.csc415.studentorganizer;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Cards.Card;
import Cards.CardAdapter;

/**
 * Created by Matt on 11/02/2017.
 */

public class course_page_shell extends Fragment {

    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;
    private DBHelper mydb;

    private int courseID;
    private String courseName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Load bundle containing information about the skill that should be displayed.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            courseID = bundle.getInt("courseTag", -1);
            courseName = bundle.getString("courseName", "DEFAULT");
        }

    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_course_page, container, false);

        mydb = new DBHelper(getActivity());

        // Populate widgets with the data retrieved from bundle that is related to a specific skill
        TextView name = (TextView) view.findViewById(R.id.course_label);
        name.setText(courseName);

        recyclerView = (RecyclerView) view.findViewById(R.id.course_tasks_recycleView);
        cAdapter = new TaskCardAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        prepareCardData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //repopulate cards
        CardList.clear();
        prepareCardData();
    } //end onResume

    private void prepareCardData(){

        int numRows = mydb.numberOfRows();
        for (int i = 1; i <= numRows; i++) {
            Card c;
            Cursor result = mydb.getActivity(i);
            if (result != null && result.getCount() > 0) {
                result.moveToFirst();
                if (result.getInt(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_COURSE_ID)) == courseID) {
                    int id = result.getInt(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_ID));
                    String title = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_TITLE));
                    String desc = result.getString(result.getColumnIndex(DBHelper.ACTIVITIES_COLUMN_DESCRIPTION));
                    if (!result.isClosed()) {
                        result.close();
                    }
                    c = new Card(id, title, desc, new Date());
                    CardList.add(c);
                }
            }
            else {
                numRows += 1;
            }
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
            holder.taskID.setText(Integer.toString(c.getTaskID()));

            // TODO fix this to handle null
            holder.icon.setImageResource(R.mipmap.ic_launcher_round);

            //Handle null string in DATE_FORMAT
            try {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy h:mm");
                holder.dueDate.setText(DATE_FORMAT.format(c.getDueDate()).toString());
            }
            catch (Exception e){
                holder.dueDate.setText("Error");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddItemActivity.class);
                    intent.putExtra("id", c.getTaskID());
                    startActivity(intent);
                }
            }); //end OnClickListener
        } //end setOnCLickListener
    } //end TaskCardAdapter class

}
