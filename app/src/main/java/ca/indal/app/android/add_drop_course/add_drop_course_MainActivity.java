package ca.indal.app.android.add_drop_course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.indal.app.android.R;
import ca.indal.app.android.model.Course;
import ca.indal.app.android.add_drop_course.viewholder.ContactViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import android.widget.TextView;

public class add_drop_course_MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_add_drop_course_main);


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
                Intent intent = new Intent(add_drop_course_MainActivity.this, DropMain.class);
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

    private void setUpRecyclerView(RecyclerView rv, FirestoreRecyclerAdapter adapter)
    {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

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
            @Override
            public void onBindViewHolder(ContactViewHolder holder, int position, final Course model)
            {
                holder.CourseName.setText(model.CourseName);
                holder.CourseID.setText(model.CourseID);

                holder.detailsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(add_drop_course_MainActivity.this,CourseDetail.class);
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


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }
}
