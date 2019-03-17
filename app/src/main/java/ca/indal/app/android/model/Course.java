package ca.indal.app.android.model;

import java.io.Serializable;

public class Course implements Serializable {

    //public String CourseName;
    public String CourseID;
    //public String CourseTime;
    public String term;
    public String time;
    public String days;
    public String credit;
    public String precourses;


    public  Course(String courseID, String s, String s1, String s2, String s3)
    {

    }

    public Course(String CourseID, String term, String time, String days, String credit, String precourses)
    {
        //this.CourseName = CourseName;
        this.CourseID = CourseID;
        //this.CourseTime=CourseTime;
        this.term=term;
        this.time = time;
        this.days = days;
        this.credit = credit;
        this.precourses = precourses;

    }


    @Override
    public String toString()
    {
        return  " CourseID: " + CourseID+  " Course Term: " + term  + " startTime: "+time+ " days: "+days+" credit: " + credit + precourses;
    }
}
