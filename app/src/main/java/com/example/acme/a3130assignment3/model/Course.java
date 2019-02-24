package com.example.acme.a3130assignment3.model;

import java.io.Serializable;

/*
Simple POJO class to hold Contact information
in Realtime database or Firestore database
 */
public class Course implements Serializable {

    public String CourseName;
    public String CourseID;
    public String CourseTime;
    public String CourseProfessor;
    public String CourseFailRate;
    public String CourseAvailable;

    public  Course()
    {

    }

    public Course(String CourseName, String CourseID,String CourseTime,String CourseProfessor,String CourseFailRate,String CourseAvailable)
    {
        this.CourseName = CourseName;
        this.CourseID = CourseID;
        this.CourseTime=CourseTime;
        this.CourseProfessor=CourseProfessor;
        this.CourseFailRate=CourseFailRate;
        this.CourseAvailable=CourseAvailable;
    }


    @Override
    public String toString()
    {
        return "CourseName: " + CourseName + " CourseID: " + CourseID+ " CourseTime: " + CourseTime+ " CourseProfessor: " + CourseProfessor+ " CourseFailRate: " + CourseFailRate+ " CourseAvailable: " + CourseAvailable;
    }
}
