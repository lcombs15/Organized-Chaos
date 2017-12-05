package edu.group7.csc415.studentorganizer;

import android.app.Fragment;
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
 * Created by Matt on 11/22/2017.
 */

public class course_page_shell extends Fragment {

    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;

    private String courseTag;
    private String courseName;
    private String courseLocation;
    private String courseStart;
    private String courseEnd;
    private String courseDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Load bundle containing information about the skill that should be displayed.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            courseTag = bundle.getString("courseTag", "DEFAULT");
            courseName = bundle.getString("courseName", "DEFAULT");
            courseLocation = bundle.getString("courseLocation", "DEFAULT");
            courseStart = bundle.getString("courseStart", "DEFAULT");
            courseEnd = bundle.getString("courseEnd", "DEFAULT");
            courseDays = bundle.getString("courseDays", "DEFAULT");
        }

    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_course_page, container, false);

        // Populate widgets with the data retrieved from bundle that is related to a specific skill
        TextView name = (TextView) view.findViewById(R.id.course_label);
        TextView location = (TextView) view.findViewById(R.id.course_location_label);
        TextView startTime = (TextView) view.findViewById(R.id.course_start_time_label);
        TextView endTime = (TextView) view.findViewById(R.id.course_end_time_label);
        name.setText(courseName);
        location.setText(courseLocation);
        startTime.setText(courseStart);
        endTime.setText(courseEnd);

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

        for(int i = 1; i <= 100; i++){
            Card c = new Card(0, "Card #" + i,"Card description goes here!",new Date(),null);
            CardList.add(c);
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
                    Toast.makeText(getActivity(),"TODO: Add On Click....." + c.getTitle().toString(),Toast.LENGTH_LONG).show();
                }
            }); //end OnClickListener
        } //end setOnCLickListener
    } //end TaskCardAdapter class

}
