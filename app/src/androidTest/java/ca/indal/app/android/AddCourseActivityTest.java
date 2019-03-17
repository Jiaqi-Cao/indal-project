/*
 * @author Jinkun Chen
 * @version 1
 * @time: 3.13
 * @author Xuemin Yu
 * @version 2
 * @time: 3.15
 * */

package ca.indal.app.android;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddCourseActivityTest {

    /*
     * The test check the web and csv that is exist or not
    */

    @Test
    public void validurl() throws IOException {
        String validurl = "http://app.indal.ca";
        URL url = new URL(validurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int status = connection.getResponseCode();
        assertEquals(200, status);
    }

    @Test
    public void validcsv() throws IOException{
        String validcsv = "http://app.indal.ca/wp-content/tables/available-term.csv";
        URL url2 = new URL(validcsv);
        HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
        int status2 = connection2.getResponseCode();
        assertEquals(200, status2);
    }


   /* @Rule
    public ActivityTestRule<AddCourseActivity> addActivityRule =
            new ActivityTestRule<>(AddCourseActivity.class);

    /*
     * The test check the floating button
     */
    /*@Test
    public void button() {
        onView(withId(R.id.fab)).perform(click());

    }*/


    /*
     * The test check the alertdialog
     */
   /* @Test
    public void alertdialog() {
        onView(withText("Term Selection")).check(matches(isDisplayed()));
        onView(withText(android.R.id.button1))
                .inRoot(isDialog())
                .check(matches(withText("CLOSE")))
                .check(matches(isDisplayed()));
        onView(withText(android.R.id.button2))
                .inRoot(isDialog())
                .check(matches(withText("ADD COURSE")))
                .check(matches(isDisplayed()));
        onView(withText("2018 Fall Term")).perform(click());
        onView(withText("2018 Winter Term")).perform(click());
        onView(withText("2018 Summer Term")).perform(click());

    }*/

    /*
     * The test check the Toast notify
     */
   /* @Test
    public void toast() {
        onView(withText("2018 Fall Term"))
                .inRoot(withDecorView((Matcher<View>) not((Validator) addActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(withText("2018 Fall Term")));

        onView(withText("2018 Winter Term"))
                .inRoot(withDecorView((Matcher<View>) not((Validator) addActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(withText("2018 Winter Term")));

        onView(withText("2018 Summer Term"))
                .inRoot(withDecorView((Matcher<View>) not((Validator) addActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(withText("2018 Summer Term")));

        onView(withText(android.R.id.button2))
                .inRoot(withDecorView((Matcher<View>) not((Validator) addActivityRule.getActivity().getWindow().getDecorView())))
                .check(matches(withText("Successful")));
    }*/









}