package ca.indal.app.android.model;

import java.io.Serializable;

public class Course implements Serializable {

    //public String CourseName;
    public String CourseID;
    //public String CourseTime;
    public String term;

    public  Course()
    {

    }

    public Course(String CourseID, String term)
    {
        //this.CourseName = CourseName;
        this.CourseID = CourseID;
        //this.CourseTime=CourseTime;
        this.term=term;
    }


    @Override
    public String toString()
    {
        return  " CourseID: " + CourseID+  " Course Term: " + term;
    }
}
