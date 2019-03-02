package ca.indal.app.android.add_drop_course.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.indal.app.android.R;

public class ContactViewHolder extends RecyclerView.ViewHolder  {

    public TextView CourseName;
    public TextView CourseID;
    public Button detailsButton;

    public ContactViewHolder(View view)
    {
        super(view);
        CourseName = view.findViewById(R.id.CourseName);
        CourseID = view.findViewById(R.id.CourseID);
        detailsButton = view.findViewById(R.id.goDetails);

    }




}
