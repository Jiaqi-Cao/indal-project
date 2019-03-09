package ca.indal.app.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ca.indal.app.android.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class UserInformation extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView b_num;
    private TextView uid;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private Intent intent;
    private User curr_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        b_num = findViewById(R.id.bn);
        uid = findViewById(R.id.uid);


        auth = FirebaseAuth.getInstance();

        intent = getIntent();

        final FirebaseUser curr_user = FirebaseAuth.getInstance().getCurrentUser();
        if(curr_user != null) {
            email.setText(curr_user.getEmail());
            uid.setText(curr_user.getUid());
        }else{
            name.setText("Can't get user name");
            email.setText("Can't get user email");
            b_num.setText("Can't find user B-number");
            uid.setText("No information");
        }

    }

}
