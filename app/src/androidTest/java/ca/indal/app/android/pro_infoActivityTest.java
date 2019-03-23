/*
 * @author Xuemin Yu, Jiaqi Cao
 * @version 1
 * @time:3.22
 * */

package ca.indal.app.android;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.test.filters.LargeTest;

import static org.junit.Assert.assertEquals;


@LargeTest
public class pro_infoActivityTest {

    /*
     * The test check the web that is exist or not
     */

    @Test
    public void validurl() throws IOException {
        String validurl = "http://prof.app.indal.ca/";
        URL url = new URL(validurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200, status);
    }

}