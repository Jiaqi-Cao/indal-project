/*
 * @author Yang Shu, Jessie Wang
 * @version 0.1
 * User object for received the information for current user who login in the application
 * version 0.2
 * add new method for the new perimeter name and b number
 * version 0.3
 * rework for the new user object for the new future
 * @time: 3.8
 */
package ca.indal.app.android.model;

import java.io.Serializable;

public class User implements Serializable {

    public String email;
    public String UID;
    public String role;
    public String name;
    public String bn;

    public User()
    {

    }

    public User(String email, String UID, String role)
    {
        this.email = email;
        this.UID = UID;
        this.role = role;
    }

    /*
    * get and set method for perimeters
    * @return String
    */
    public String getEmail() {
        return email;
    }

    public String getUID() {
        return UID;
    }

    public String getName() { return name; }

    public String getBn() { return bn; }

    public void setName(String name) { this.name = name; }

    public void setBn(String bn) { this.bn = bn; }

    /*
    * To string method return the email and uid in the firebase
    * */
    @Override
    public String toString()
    {
        return "Email: " + email + " UID: " + UID;
    }
}
