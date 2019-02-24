package com.example.coursetress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button comm;
    Button math;
    Button cs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: Stared.");
        comm = (Button) findViewById(R.id.button2);
        comm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ImageView comm = (ImageView) findViewById(R.id.Tree);

                comm.setImageResource(imageRecource);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        } )    ;
        cs = (Button) findViewById(R.id.button3);

        math = (Button) findViewById(R.id.button4);
    }
}
