package com.example.acme.a3130assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.example.acme.a3130assignment3.model.Course;
import com.google.firebase.firestore.FirebaseFirestore;
import android.view.View;

public class DropDetail extends AppCompatActivity {

    private TextView CourseName;
    private TextView CourseID;
    private TextView CourseTime;
    private TextView CourseProfessor;
    private TextView CourseFailRate;
    private TextView CourseAvailable;
    private Button Drop;
    private String x;

    private FirebaseFirestore database;
    private Intent intent;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_detail);

        CourseName = findViewById(R.id.CourseName);
        CourseID = findViewById(R.id.CourseID);
        CourseTime = findViewById(R.id.CourseTime);
        CourseProfessor = findViewById(R.id.CourseProfessor);
        CourseFailRate = findViewById(R.id.CourseFailRate);
        CourseAvailable = findViewById(R.id.CourseAvailable);
        Drop = findViewById(R.id.Drop);

        database = FirebaseFirestore.getInstance();

        intent = getIntent();

        course = (Course) intent.getSerializableExtra("Course");
        CourseName.setText(course.CourseName);
        CourseID.setText(course.CourseID);
        CourseTime.setText(course.CourseTime);
        CourseProfessor.setText(course.CourseProfessor);
        CourseFailRate.setText(course.CourseFailRate);
        CourseAvailable.setText(course.CourseAvailable);

        Drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DocumentReference ref = database.collection("User/"+x+"/course").document(course.CourseID);
                ref.delete();
                finish();
            }
        });




    }

    }


