package edu.group7.csc415.studentorganizer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CourseMainActivity extends FrameLayout
{
    private TextView textViewClass1;
    private TextView textViewClass2;
    private TextView textViewClass3;
    private TextView textViewClass4;
    private TextView textViewClass5;

    public CourseMainActivity(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_course_main, this);
        initView();
    }

    private void initView()
    {
        //textViewClass1 = (TextView)findViewById(R.id.Course1);
        //textViewClass2 = (TextView)findViewById(R.id.Course2);
        //textViewClass3 = (TextView)findViewById(R.id.Course3);
        //textViewClass4 = (TextView)findViewById(R.id.Course4);
        //textViewClass5 = (TextView)findViewById(R.id.Course5);

    }

    public void setClasses(String[] classes)
    {
        textViewClass1.setText(classes[0]);
        textViewClass2.setText(classes[1]);
        textViewClass3.setText(classes[2]);
        textViewClass4.setText(classes[3]);
        textViewClass5.setText(classes[4]);
    }

}