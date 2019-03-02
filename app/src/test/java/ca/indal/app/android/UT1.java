import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals("CSCI1100",new Course("CSCI1100").getCourseName() );
        assertEquals("victor",new Course("victor").getUserName() );

    }

}