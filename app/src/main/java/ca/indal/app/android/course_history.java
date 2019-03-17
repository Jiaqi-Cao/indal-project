/*
 * @author Jiaqi Cao
 * @version 1
 * @author Sherry Kuang
 * @version 2
 * */
package ca.indal.app.android;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class course_history extends AppCompatActivity {

    public TextView cid;
    public TextView credit;
    public TextView remain;
    public String CourseID;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        //@Override
        public void handleMessage(Message msg) {
            cid = findViewById(R.id.cid);
            credit = findViewById(R.id.credit);
            remain = findViewById(R.id.remain);

            if(msg.what == 1){
                System.out.println(CourseID);
                cid.setText(CourseID+"");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int state = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_history);
        cid = (TextView) findViewById(R.id.cid);
        credit = (TextView) findViewById(R.id.credit);
        remain = (TextView) findViewById(R.id.remain);

        readJson();


    }
    /*
     *This method will read the json file from online and get the data for the courses
     * the print them on the UI
     */
    public void readJson() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    URLConnection connection = url.openConnection();//获取互联网连接
                    InputStream is = connection.getInputStream();//获取输入流
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");//字节转字符，字符集是utf-8
                    BufferedReader bufferedReader = new BufferedReader(isr);//通过BufferedReader可以读取一行字符串
                    String jsonData = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.i("Output：", "" + line);
                        jsonData = jsonData + line;
                    }
                    Log.i("json：", "" + jsonData);
                    bufferedReader.close();
                    //jsonFilter(jsonData);
                    try {
                        JSONObject jsonObject1 = new JSONObject(jsonData);
                        String json_fields = jsonObject1.getString("CourseID");
                        Log.i("<--- Json -->", json_fields);
                        CourseID = json_fields;
                    }
                    catch (JSONException e) {
                        Log.i("json：", "???" );
                        e.printStackTrace();
                    }
                    isr.close();
                    is.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cid.setText(CourseID);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("https://us-central1-universityapp-10e74.cloudfunctions.net/allCredit?uid=8NyHq3daCGgbMtTqomGOMYaZLY53");

    }
}
