package ca.indal.app.android.add_drop_course;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.indal.app.android.R;
import ca.indal.app.android.add_drop_course.model.Course;
import ca.indal.app.android.add_drop_course.viewholder.ContactViewHolder2;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DropMain extends AppCompatActivity {

    private FirebaseFirestore database;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_main);

        recyclerView = findViewById(R.id.contactlist);
        database = FirebaseFirestore.getInstance();
        adapter = setUpAdapter(database);
        setUpRecyclerView(recyclerView,adapter);


    }
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
        auth = FirebaseAuth.getInstance();
        String x = auth.getUid();
        Query query = db.collection("User/"+x+"/course").orderBy("CourseName").limit(50);
        FirestoreRecyclerOptions<Course> options = new FirestoreRecyclerOptions.Builder<Course>()
                .setQuery(query,Course.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Course,ContactViewHolder2>(options)
        {
            //For each item in the database connect it to the view
            @Override
            public void onBindViewHolder(ContactViewHolder2 holder, int position, final Course model)
            {
                holder.CourseName.setText(model.CourseName);
                holder.CourseID.setText(model.CourseID);

                //Set the on click for the button
                //I find this ugly :) but it is how you will see in most examples
                // You CAN use lambadas for the listeners
                // e.g. setOnClickListener ((View v) -> ....
                holder.DropButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DropMain.this,DropDetail.class);
                        intent.putExtra("Course",model);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public ContactViewHolder2 onCreateViewHolder(ViewGroup group, int i)
            {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.activity_contact_view_holder2,group,false);
                return new ContactViewHolder2(view);

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


