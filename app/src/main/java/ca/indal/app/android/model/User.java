package ca.indal.app.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    public String email;
    public String UID;
    public String role;
    public List<String> terms = new ArrayList<>();

    public User()
    {

    }

    public User(String email, String UID,String role)
    {
        this.email = email;
        this.UID = UID;
        this.role = role;
    }

    public void setTerms(List<String> terms){
        this.terms = terms;
    }

    public List<String> getTerms(){
        return terms;
    }


    @Override
    public String toString()
    {
        return "Email: " + email + " UID: " + UID+ " role: " + role;
    }
}
