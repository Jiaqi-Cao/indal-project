package ca.indal.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CourseTreeActivity extends AppCompatActivity {

    private Button mathbtn, commbtn, cscibtn;
    private ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tree);

        mathbtn = findViewById(R.id.math);
        commbtn = findViewById(R.id.comm);
        cscibtn = findViewById(R.id.csci);

        view = findViewById(R.id.imageView);
        mathbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.math);
            }
        });

        commbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.comm);
            }
        });

        cscibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.cs);
            }
        });
    }
}
