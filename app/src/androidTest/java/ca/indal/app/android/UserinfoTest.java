package ca.indal.app.android;

import android.support.test.runner.AndroidJUnit4;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * @author Yang Shu
 * @version 1.0
 * Testing is current login in user login successfully
 */
@RunWith(AndroidJUnit4.class)
public class UserinfoTest {
    private FirebaseUser current_uesr;
    private FirebaseAuth auth;

    @Test
    public void useAppContext() throws Exception{
        auth = FirebaseAuth.getInstance();
        current_uesr = FirebaseAuth.getInstance().getCurrentUser();
        boolean user_email_test = auth.getCurrentUser().getEmail().equals(current_uesr.getEmail());
        boolean user_uid_test = auth.getCurrentUser().getUid().equals(current_uesr.getUid());
        assertEquals(true, (user_email_test && user_uid_test));

    }
}
