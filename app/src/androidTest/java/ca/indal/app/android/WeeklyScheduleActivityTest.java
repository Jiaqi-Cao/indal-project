/*
 * @author Xuemin Yu, Jinkun Chen
 * @version 1
 * @time:3.16
 * */

package ca.indal.app.android;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;


@LargeTest
public class WeeklyScheduleActivityTest {

    /*
     * The test check the web and csv that is exist or not
     */
    @Test
    public void validurl() throws IOException {
        String uid = "7mFXF2UG8aOGeck25HhjaJXFMPm1";
        String term = "201801";
        String validurl = "http://app.indal.ca/api/timetable/?uid=" + uid + "&term=" + term;
        URL url = new URL(validurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200, status);
    }

    @Test
    public void validcsv()throws IOException {
        String validcsv = "http://app.indal.ca/wp-content/tables/available-term.csv";
        URL url2 = new URL(validcsv);
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        int status2 = connection2.getResponseCode();
        assertEquals(200, status2);
    }
}
