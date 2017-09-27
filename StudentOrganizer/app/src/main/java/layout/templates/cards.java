package layout.templates;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.group7.csc415.studentorganizer.R;

public class cards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);


        LinearLayoutCompat cards_go_here = (LinearLayoutCompat) findViewById(R.id.cards_go_here);

        //View card = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_card,cards_go_here,false);
        View card =  View.inflate(getApplicationContext(), R.layout.activity_card, null);
        card.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
        ( (TextView) card.findViewById(R.id.card_title)).setText("TITLE HERE");
        ( (TextView) card.findViewById(R.id.card_detail)).setText("STUFF HERE");

        card.setBackgroundColor(Color.parseColor("#0000FF"));
        cards_go_here.addView(card);





    }
}
