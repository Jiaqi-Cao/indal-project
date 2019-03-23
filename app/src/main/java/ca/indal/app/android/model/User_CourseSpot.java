package ca.indal.app.android.model;

import java.io.Serializable;

public class User_CourseSpot implements Serializable {

    public String UID;

    public User_CourseSpot()
    {

    }

    public User_CourseSpot(String UID)
    {
        this.UID = UID;
    }


    @Override
    public String toString()
    {
        return " UID: " + UID;
    }
}
