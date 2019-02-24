/*
        package com.example.acme.a3130assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acme.a3130assignment3.model.Contact;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddContact extends AppCompatActivity {

    private TextView CourseName;
    private TextView CourseHour;
    private TextView CourseTime;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        CourseName = findViewById(R.id.CourseName);
        CourseHour = findViewById(R.id.CourseHour);
        CourseTime = findViewById(R.id.CourseTime);
        businessNumber = findViewById(R.id.businessNumber);
        PrimaryBusiness = findViewById(R.id.PrimaryBusiness);
        Address = findViewById(R.id.Address);
        Province1 = findViewById(R.id.Province1);

        database = FirebaseFirestore.getInstance();

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contact c = new Contact(name.getText().toString(),email.getText().toString(),businessNumber.getText().toString(),PrimaryBusiness.getText().toString(),Address.getText().toString(),Province1.getText().toString());

                //Here instead of adding directly we are first getting a reference so we save the ID;
                // this is not necessary but it will make life easier latter when editing/deleting.
                DocumentReference ref = database.collection("contacts").document();
                c.id = ref.getId();
                ref.set(c);

                //Finishes the acitivy and return to the parent one.
                finish();
            }
        });

    }
}
*/