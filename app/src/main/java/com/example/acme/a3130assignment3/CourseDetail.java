package com.example.acme.a3130assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.example.acme.a3130assignment3.model.Course;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acme.a3130assignment3.model.Course;
import com.example.acme.a3130assignment3.viewholder.ContactViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Console;

public class CourseDetail extends AppCompatActivity {

    private TextView CourseName;
    private TextView CourseID;
    private TextView CourseTime;
    private TextView CourseProfessor;
    private TextView CourseFailRate;
    private TextView CourseAvailable;
    private Button Add;

    private FirebaseFirestore database;
    private Intent intent;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

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

                //Here instead of adding directly we are first getting a reference so we save the ID;
                // this is not necessary but it will make life easier latter when editing/deleting.
                DocumentReference ref = database.collection("User/jqNFtHORDKHlQGQP6wZY/course").document(course.CourseID);
                c.CourseID = ref.getId();
                ref.set(c);


                //Finishes the acitivy and return to the parent one.
                finish();
            }
        });
        /*
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });



    }

    //TODO: add the logic for updating an entry
    private void updateContact()
    {
        DocumentReference ref = database.collection("contacts").document(contact.id);
        ref.update("name", name.getText().toString(), "email",email.getText().toString(), "businessNumber" , businessNumber.getText().toString(), "PrimaryBusiness",PrimaryBusiness.getText().toString(), "Address",Address.getText().toString(), "Province1",Province1.getText().toString());
        finish();
    }

    //TODO: add the logic for deleting an entry
    private void deleteContact()
    {
        DocumentReference ref = database.collection("contacts").document(contact.id);
        ref.delete();
        finish();

    }
*/
    }
}
