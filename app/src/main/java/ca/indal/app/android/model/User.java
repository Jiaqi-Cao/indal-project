package ca.indal.app.android.model;

import java.io.Serializable;

public class User implements Serializable {

    public String email;
    public String UID;
    public String role;

    public User()
    {

    }

    public User(String email, String UID,String role)
    {
        this.email = email;
        this.UID = UID;
        this.role=role;
    }

    public String getEmail() {
        return email;
    }

    public String getUID() {
        return UID;
    }

    @Override
    public String toString()
    {
        return "Email: " + email + " UID: " + UID+ " role: " + role;
    }
}
