package ca.indal.app.android;

import android.webkit.WebView;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;


public class NewsTest{

    @Test
    public void urltest() throws IOException{
        String urltest = "http://news.app.indal.ca/";
        URL url = new URL(urltest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200,status);
    }

}