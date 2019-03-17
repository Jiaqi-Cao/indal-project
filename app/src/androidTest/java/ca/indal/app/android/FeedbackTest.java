package ca.indal.app.android;


/*
 * @ author Jessie Wang
 * @ version 1
 * @ author Yang Shu
 * @ version
 * @ time 3.13
 * */
import org.junit.Test;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;


public class FeedbackTest{


    private static String urltest = "http://news.app.indal.ca/feedback/";



    /* this method tests the feedback
     * @ return nothing
     * */
    @Test
    public void urltest() throws IOException{
        URL url = new URL(urltest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200,status);
    }
}
