package ca.indal.app.android.add_drop_course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import ca.indal.app.android.R;
import ca.indal.app.android.add_drop_course.model.Course;
import ca.indal.app.android.model.User;

import com.google.firebase.firestore.FirebaseFirestore;
import android.view.View;

public class CourseDetail extends AppCompatActivity {

    private TextView CourseName;
    private TextView CourseID;
    private TextView CourseTime;
    private TextView CourseProfessor;
    private TextView CourseFailRate;
    private TextView CourseAvailable;
    private Button Add;
    private FirebaseAuth auth;
    private String x;

    private FirebaseFirestore database;
    private Intent intent;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        auth = FirebaseAuth.getInstance();
        x = auth.getUid();

        CourseName = findViewById(R.id.CourseName);
        CourseID = findViewById(R.id.CourseID);
        CourseTime = findViewById(R.id.CourseTime);
        CourseProfessor = findViewById(R.id.CourseProfessor);
        CourseFailRate = findViewById(R.id.CourseFailRate);
        CourseAvailable = findViewById(R.id.CourseAvailable);
        Add = findViewById(R.id.Add);

        database = FirebaseFirestore.getInstance();

        intent = getIntent();

        course = (Course) intent.getSerializableExtra("Course");
        CourseName.setText(course.CourseName);
        CourseID.setText(course.CourseID);
        CourseTime.setText(course.CourseTime);
        CourseProfessor.setText(course.CourseProfessor);
        CourseFailRate.setText(course.CourseFailRate);
        CourseAvailable.setText(course.CourseAvailable);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Course c = new Course(CourseName.getText().toString(),CourseID.getText().toString(),CourseTime.getText().toString(),CourseProfessor.getText().toString(),CourseFailRate.getText().toString(),CourseAvailable.getText().toString());
                DocumentReference ref = database.collection("User/"+x+"/course").document(course.CourseID);
                c.CourseID = ref.getId();
                ref.set(c);

                /*String email = auth.getCurrentUser().getEmail();
                String UID = auth.getCurrentUser().getUid();
                String role = auth.getCurrentUser().

                User d = new User(email,UID.getText().toString(),role.getText().toString());
                DocumentReference ref2 = database.collection("Course/"+x+"/students").document(d,UID);
                d.UID = ref2.getId();
                ref2.set(d);*/

                finish();
            }
        });
    }
}
