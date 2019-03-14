/*
* @author Jinkun Chen
* @version 1
* */
package ca.indal.app.android;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import ca.indal.app.android.model.Course;
import ca.indal.app.android.model.CourseSpot;
import ca.indal.app.android.model.User_CourseSpot;


public class AddCourseActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = 0;
    private String currentURL = "";
    private String cat = "";
    private String CourseID = "";
    FloatingActionButton fab;
    private ArrayList<String> IDs = new ArrayList<String>();
    private ArrayList<String> terms = new ArrayList<String>();
    private AlertDialog alertDialog;
    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private Course course;
    private CourseSpot courseSpot;
    int choose_term_index = 0;
    private String maxspot;
    private  int max;

    private String wholeJsonData = "";
    private ArrayList<String> emailhahaha = new ArrayList<String>();

    /*
     * This method build the interface of Add Course Activity
     * @return Nothing
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        fab = findViewById(R.id.fab);

        readCSV();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termSelection();

            }
        });

        webView = findViewById(R.id.webview1);
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentURL = webView.getUrl();
                cat = "";
                CourseID = "";
                if (currentURL.split("\\/").length > 5) {
                    cat = currentURL.split("\\/")[3];
                    CourseID = currentURL.split("\\/")[5];
                }

                if (cat.equals("course")) {
                    readCourseInfoCSV(terms.get(choose_term_index));
                    fab.show();

                } else {
                    fab.hide();
                }

            }

        });
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        webView.loadUrl("http://app.indal.ca");          //调用loadUrl方法为WebView加入链接

    }


    /*
    * This method build the go back button for Add and Drop Activity
    * @return Nothong.
    * */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }

        }
    }

    /*
    * This method is used to read the CSV file from the server in order to get the available term
    * @return Nothing.
    * @exception IOException On input error.
    * @see IOException
    * */
    public void readCSV() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    URLConnection connection = url.openConnection();//获取互联网连接
                    InputStream is = connection.getInputStream();//获取输入流
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");//字节转字符，字符集是utf-8
                    BufferedReader bufferedReader = new BufferedReader(isr);//通过BufferedReader可以读取一行字符串
                    String line;
                    bufferedReader.readLine();
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.i("Output：", "" + line);
                        String item[] = line.split(",");
                        IDs.add(item[0]);
                        terms.add(item[1]);
                    }
                    bufferedReader.close();
                    isr.close();
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("http://app.indal.ca/wp-content/tables/available-term.csv");

    }

    /*
     * This method is used to create an dialog for user to choose term before course selection
     * @return Nothing.
     * */
    public void termSelection() {
        auth = FirebaseAuth.getInstance();
        final String authUid = auth.getUid();
        database = FirebaseFirestore.getInstance();
        //int choose = -1;
        //course = (Course) intent.getSerializableExtra("Course");

        final String items[] = (String[])terms.toArray(new String[terms.size()]);;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Term Selection")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(AddCourseActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddCourseActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        choose_term_index = which;
                        Toast.makeText(AddCourseActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Add Course", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Course c = new Course();
                        if(!CourseID.equals("")){
                            //Course c = new Course(value,IDs.get(choose_term_index));
                            //readCourseInfoCSV(terms.get(choose_term_index));
                            DocumentReference ref = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document(CourseID);
                            DocumentReference ref2 = database.collection("CourseSpot/").document(CourseID);
                            ref.set(course);
                            ref2.set(courseSpot);
                            DocumentReference ref3 = database.collection("CourseSpot/"+CourseID+"/"+IDs.get(choose_term_index)).document(authUid);
                            ref3.set(new User_CourseSpot(auth.getUid()));

                            Toast.makeText(AddCourseActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                            //Every time, if user click "add course" button, then the available spot minus one until zero
                            if(max != 0){
                                max--;
                                Toast.makeText(AddCourseActivity.this, "Available Spots: "+ max, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AddCourseActivity.this, "Unsuccessful! Remaining zero seats", Toast.LENGTH_SHORT).show();
                            }

                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /*
     * This method is used to read the CSV file from the server in order to get the course information
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     * */
    public void readCourseInfoCSV(String term) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    URLConnection connection = url.openConnection();//获取互联网连接
                    InputStream is = connection.getInputStream();//获取输入流
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");//字节转字符，字符集是utf-8
                    BufferedReader bufferedReader = new BufferedReader(isr);//通过BufferedReader可以读取一行字符串
                    String line;
                    bufferedReader.readLine();
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.i("Output：", "" + line);
                        String item[] = line.split(",");
                        course = new Course(CourseID, IDs.get(choose_term_index), item[2], item[3], item[7]);

                        //course read max spot in the course and record it
                        maxspot = item[6];
                        max = Integer.parseInt(maxspot);
                        System.out.println("MaxSpots: " + max);
                        //max = Integer.parseInt(maxspot);
                        courseSpot = new CourseSpot(CourseID);

                    }
                    bufferedReader.close();
                    isr.close();
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("http://app.indal.ca/wp-content/tables/"+CourseID+".csv");
    }

    /*public void jsonFilter(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator keys = jsonObject.keys();
            Log.i("<--- Json -->", jsonObject.getString("createTime"));
            //通过迭代器获得json当中所有的key值
            //然后通过循环遍历出的key值
            while (keys.hasNext()){
                String key = String.valueOf(keys.next());
                //Log.i("!!!!!!->json：", "" + key);
                //通过通过刚刚得到的key值去解析后面的json了
            }
        }
        catch (JSONException e) {
            //Log.i("json：", "???" );
            e.printStackTrace();
        }
        //return
    }*/

}