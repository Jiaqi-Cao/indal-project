package ca.indal.app.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedIndex implements Serializable {

    public List<String> courses = new ArrayList<>();

    public SelectedIndex(){

    }
    public SelectedIndex(List<String> courses){
        this.courses = courses;
    }
    public List<String> returnTheArray(){
        return courses;
    }
    public void setCourses(List<String> courses){
        this.courses = courses;
    }
    @Override
    public String toString()
    {
        return "courses: " + courses;
    }
}
