package ca.indal.app.android.model;

import java.io.Serializable;

public class Course implements Serializable {

    //public String CourseName;
    public String CourseID;
    //public String CourseTime;
    public String term;
    public String max;
    public String time;
    public String days;
    public String credit;


    public  Course()
    {

    }

    public Course(String CourseID, String term, String max , String time, String days, String credit)
    {
        //this.CourseName = CourseName;
        this.CourseID = CourseID;
        //this.CourseTime=CourseTime;
        this.term=term;
        this.max = max;
        this.time = time;
        this.days = days;
        this.credit = credit;

    }


    @Override
    public String toString()
    {
        return  " CourseID: " + CourseID+  " Course Term: " + term + " Max Spots:  "+ max + " startTime: "+time+ " days: "+days+" credit: " + credit;
    }
}
