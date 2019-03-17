package ca.indal.app.android;



import org.junit.Test;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;


public class NewsTest{


    private static String urltest = "http://news.app.indal.ca/";




    @Test
    public void urltest() throws IOException{
        URL url = new URL(urltest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200,status);
    }
}