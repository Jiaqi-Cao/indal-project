/*
 * @author Xuemin Yu
 * @version 1
 * @time:3.15
 * */

package ca.indal.app.android;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import androidx.test.filters.LargeTest;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImportantDatesActivityTest {

    /*
     * The test check the web that is exist or not
     */

   @Test
   public void validurl() throws IOException {
       String validurl = "http://news.app.indal.ca/important-dates/";
       URL url = new URL(validurl);
       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
       int status = connection.getResponseCode();
       assertEquals(200, status);
   }

}