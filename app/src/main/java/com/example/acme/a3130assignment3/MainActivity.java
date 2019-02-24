package com.example.acme.a3130assignment3;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button Drop;
    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;
    private Button Search;
    private Button AllCourses;
    private TextView SearchBar;
    private Course course;
    private String x;
    private int y=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.contactlist);
        Search = findViewById(R.id.Search);
        AllCourses = findViewById(R.id.AllCourses);
        SearchBar = findViewById(R.id.SearchBar);
        database = FirebaseFirestore.getInstance();
        Drop = findViewById(R.id.Drop);
        adapter = setUpAdapter(database);
        x = SearchBar.getText().toString();

        Drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DropMain.class);
                startActivity(intent);
            }
        });
        AllCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y=0;
                setUpRecyclerView(recyclerView,adapter);

            }

        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y=1;
                setUpRecyclerView(recyclerView,adapter);

            }

        });



    }

    //Connects our recycler view with the viewholder (how we want to show our model[data])
    // and the firestore adapter
    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter)
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }


    //Creates a Firestore adapter to be used with a Recycler view.
    //We will see adapter in more details after the midterm
    //More info on this: https://github.com/firebase/FirebaseUI-Android/blob/master/firestore/README.md
    private FirestoreRecyclerAdapter setUpAdapter(FirebaseFirestore db)
    {
        Query query;
        if(y==0) {
            query = db.collection("Course").orderBy("CourseID").limit(50);
        }else {
            query = db.collection("Course").endAt(x);
        }
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query,Course.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,ContactViewHolder>(options)
        {
            //For each item in the database connect it to the view
            @Override
            public void onBindViewHolder(ContactViewHolder holder, int position, final Course model)
            {
                holder.CourseName.setText(model.CourseName);
                holder.CourseID.setText(model.CourseID);

                //Set the on click for the button
                //I find this ugly :) but it is how you will see in most examples
                // You CAN use lambadas for the listeners
                // e.g. setOnClickListener ((View v) -> ....
                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,CourseDetail.class);
                        intent.putExtra("Course",model);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public ContactViewHolder onCreateViewHolder(ViewGroup group, int i)
            {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.contact_entry,group,false);
                return new ContactViewHolder(view);

            }
        };

        return adapter;

    }


    //Method called every time the activity starts.
    @Override
    protected void onStart() {
        super.onStart();
        //Tells the adapter to start listening for changes in the database
        adapter.startListening();
    }

    //Method called every time the activity stops
    @Override
    protected void onStop() {
        super.onStop();
        //Tells the adapter to stop listening since we are not using this activity
        //  anymore. Otherwise the adapter would still exist in the background draining battery
        //  with useful cycles...
        adapter.stopListening();
    }
}
