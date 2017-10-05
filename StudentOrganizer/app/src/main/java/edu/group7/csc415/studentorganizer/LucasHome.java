package edu.group7.csc415.studentorganizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

<<<<<<< HEAD:StudentOrganizer/app/src/main/java/edu/group7/csc415/studentorganizer/LucasHome.java
public class LucasHome extends AppCompatActivity {
=======
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import layout.templates.Page.*; //We're at some point or another going to need everything in here
>>>>>>> Lucas:StudentOrganizer/app/src/main/java/edu/group7/csc415/studentorganizer/Home.java

public class Home extends AppCompatActivity {
    private List<Card> CardList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CardAdapter cAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:StudentOrganizer/app/src/main/java/edu/group7/csc415/studentorganizer/LucasHome.java
        setContentView(R.layout.activity_home_lucas);
=======
        setContentView(R.layout.activity_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        cAdapter = new CardAdapter(CardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        prepareCardData();

    }

    private void prepareCardData(){

        for(int i = 1; i <= 100; i++){
            Card c = new Card("Card #" + i,"Card description goes here!",new Date(),null);
            CardList.add(c);
        }
>>>>>>> Lucas:StudentOrganizer/app/src/main/java/edu/group7/csc415/studentorganizer/Home.java
    }
}
