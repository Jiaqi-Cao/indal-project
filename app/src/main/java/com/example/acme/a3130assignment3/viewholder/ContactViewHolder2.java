package com.example.acme.a3130assignment3.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.acme.a3130assignment3.R;

public class ContactViewHolder2 extends RecyclerView.ViewHolder  {

    public TextView CourseName;
    public TextView CourseID;
    public Button DropButton;

    public ContactViewHolder2(View view)
    {
        super(view);
        CourseName = view.findViewById(R.id.CourseName);
        CourseID = view.findViewById(R.id.CourseID);
        DropButton = view.findViewById(R.id.Drop);

    }




}
