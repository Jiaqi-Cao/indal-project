package ca.indal.app.android;

import android.util.Log;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.*;
public class AddCourseActivityTest {
    @Test
    public void validConnection() throws IOException {
        String validurl = "http://app.indal.ca";
        URL url = new URL(validurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200, status);
    }

    @Test
    public void validcsvjason()throws IOException {

        String validreadcsv = "http://app.indal.ca/wp-content/tables/csci4110.csv";
        URL url2 = new URL(validreadcsv);
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        int status2 = connection2.getResponseCode();
        assertEquals(200, status2);

        String validreadjson= "https://us-central1-universityapp-10e74.cloudfunctions.net/numStudents?course=csci4110";
        URL url3 = new URL(validreadjson);
        HttpURLConnection connection3 = (HttpURLConnection) url3.openConnection();
        int status3 = connection3.getResponseCode();
        assertEquals(200, status3);

    }

    @Test
    public void timeCheck()throws IOException {
        URL url4 = new URL("http://app.indal.ca/api/selectCheck/?course=csci1100&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        URLConnection connection4 = url4.openConnection();
        InputStream is = connection4.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String jsonData = "";
        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        String status4 = "";
        String illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        }catch (Exception e){
            System.out.print(e.toString());
        }
        assertEquals("OK", status4);
        assertEquals("", illustrate);

        url4 = new URL("http://app.indal.ca/api/selectCheck/?course=math1000&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        connection4 = url4.openConnection();
        is = connection4.getInputStream();
        isr = new InputStreamReader(is, "utf-8");
        bufferedReader = new BufferedReader(isr);
        jsonData = "";
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        status4 = "";
        illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        }catch (Exception e){
            System.out.print(e.toString());
        }
        assertEquals("No", status4);
        assertEquals("Time Confilct. ", illustrate);


    }

    @Test
    public void selectcheck()throws IOException {
        URL url4 = new URL("http://app.indal.ca/api/selectCheck/?course=csci1100&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        URLConnection connection4 = url4.openConnection();
        InputStream is = connection4.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String jsonData = "";
        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        String status4 = "";
        String illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        }catch (Exception e){
            System.out.print(e.toString());
        }
        assertEquals("OK", status4);
        assertEquals("", illustrate);

        url4 = new URL("http://app.indal.ca/api/selectCheck/?course=test&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        connection4 = url4.openConnection();
        is = connection4.getInputStream();
        isr = new InputStreamReader(is, "utf-8");
        bufferedReader = new BufferedReader(isr);
        jsonData = "";
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        status4 = "";
        illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        }catch (Exception e){
            System.out.print(e.toString());
        }
        assertEquals("No", status4);
        assertEquals("Course Full. ", illustrate);
    }

    @Test
    public void requiredCheck()throws IOException {
        URL url4 = new URL("http://app.indal.ca/api/selectCheck/?course=csci1100&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        URLConnection connection4 = url4.openConnection();
        InputStream is = connection4.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String jsonData = "";
        String line;
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        String status4 = "";
        String illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        assertEquals("OK", status4);
        assertEquals("", illustrate);

        url4 = new URL("http://app.indal.ca/api/selectCheck/?course=csci4110&term=201801&uid=uXNhPHdZ6OeJmJHfulSw3nsB9wj2");
        connection4 = url4.openConnection();
        is = connection4.getInputStream();
        isr = new InputStreamReader(is, "utf-8");
        bufferedReader = new BufferedReader(isr);
        jsonData = "";
        bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            jsonData = jsonData + line;
        }
        bufferedReader.close();
        isr.close();
        is.close();

        status4 = "";
        illustrate = "";
        try {
            JSONObject jsonObject1 = new JSONObject(jsonData);
            status4 = jsonObject1.getString("status");
            illustrate = jsonObject1.getString("illustrate");
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        assertEquals("No", status4);
        assertEquals("Miss Required Course. ", illustrate);
    }

}
