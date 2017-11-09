package edu.group7.csc415.studentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Matt on 11/8/2017.
 */

public class course_list_activity extends AppCompatActivity implements View.OnClickListener {

    TextView courseText0;
    TextView courseText1;
    TextView courseText2;
    TextView courseText3;
    TextView courseText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

        courseText0 = (TextView) findViewById(R.id.Course0);
        courseText1 = (TextView) findViewById(R.id.Course1);
        courseText2 = (TextView) findViewById(R.id.Course2);
        courseText3 = (TextView) findViewById(R.id.Course3);
        courseText4 = (TextView) findViewById(R.id.Course4);

        courseText0.setOnClickListener(this);
        courseText1.setOnClickListener(this);
        courseText2.setOnClickListener(this);
        courseText3.setOnClickListener(this);
        courseText4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Course0:
                    Intent intent = new Intent(course_list_activity.this, course_page_shell.class);
                    startActivity(intent);
        }
    }
}
