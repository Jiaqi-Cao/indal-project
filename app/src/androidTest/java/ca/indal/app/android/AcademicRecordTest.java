/*
 * @author Jinkun Chen
 * @version 1
 * @author Jiaqi Cao
 * @version 2
 * @author Sherry Kuang
 * @version 3
 * */
package ca.indal.app.android;

        import org.junit.Test;

        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.URL;

        import static org.junit.Assert.*;
public class AcademicRecordTest {
    @Test
    public void validConnection() throws IOException {
        String validurl = "http://app.indal.ca";
        URL url = new URL(validurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200, status);

        String validreadcsv = "http://app.indal.ca/wp-content/tables/csci4110.csv";
        URL url2 = new URL(validreadcsv);
        HttpURLConnection connection2 = (HttpURLConnection) url.openConnection();
        int status2 = connection.getResponseCode();
        assertEquals(200, status2);

        String validreadjson= "https://us-central1-universityapp-10e74.cloudfunctions.net/numStudents?course=csci4110";
        URL url3 = new URL(validreadjson);
        HttpURLConnection connection3 = (HttpURLConnection) url.openConnection();
        int status3 = connection.getResponseCode();
        assertEquals(200, status3);
    }

}