package ca.indal.app.android;



import org.junit.Test;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

/*
 * @ author Jessie Wang
 * @ version 1
 * @ author Yang Shu
 * @ version
 * @ time 3.8
 * */
public class NewsTest{


    private static String urltest = "http://news.app.indal.ca/";


/* this method tests the news
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