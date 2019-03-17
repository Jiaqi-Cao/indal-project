package ca.indal.app.android.model;

import java.io.Serializable;

public class CourseSpot implements Serializable {

    public String CourseID;


    public  CourseSpot()
    {

    }

    public CourseSpot(String CourseID)
    {
        //this.CourseName = CourseName;
        this.CourseID = CourseID;
        //this.CourseTime=CourseTime;
    }


    @Override
    public String toString()
    {
        return  " CourseID: " + CourseID;
    }
}
