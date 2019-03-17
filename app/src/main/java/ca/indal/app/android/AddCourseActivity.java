/*
* @author Jinkun Chen
* @version 1
* */
package ca.indal.app.android;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.indal.app.android.model.Course;
import ca.indal.app.android.model.CourseSpot;
import ca.indal.app.android.model.SelectedIndex;
import ca.indal.app.android.model.User;
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
    String currentNumStudent = "";

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

        final String items[] = (String[])terms.toArray(new String[terms.size()]);;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Term Selection")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                            /*DocumentReference ref = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document(CourseID);
                            ref.set(course);*/
                            addCourse();
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

    /*
     * This method is used to renew the course spots for a specific course at a specific term
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     * @time 2019-3-15
     * */
    public void renewCourseSpot() {
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
                    //String jsonDoc ="";
                    String line;
                    //bufferedReader.readLine();
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.i("Output：", "" + line);
                        jsonData = jsonData + line;
                    }
                    Log.i("json：", "" + jsonData);
                    bufferedReader.close();

                    final String jsonDataFinal = jsonData;

                    DocumentReference ref2 = database.collection("CourseSpots/").document(CourseID);
                    ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("LOGGER", "There is such document！！！");
                                    currentNumStudent = document.getString(IDs.get(choose_term_index));

                                    // Read Current Student Number
                                    try {
                                        DocumentReference ref2 = database.collection("CourseSpots/").document(CourseID);
                                        JSONObject jsonObject1 = new JSONObject(jsonDataFinal);
                                        String num = jsonObject1.getString(IDs.get(choose_term_index));
                                        Log.i("<--- reNew -->", num);
                                        currentNumStudent = Integer.parseInt(num)+1+"";
                                        ref2.update(IDs.get(choose_term_index),currentNumStudent);
                                    }
                                    catch (JSONException e) {
                                        Log.i("json：", "???" );
                                        e.printStackTrace();
                                    }

                                } else {
                                    Log.d("LOGGER", "No such document！！！");
                                    DocumentReference ref2 = database.collection("CourseSpots/").document(CourseID);
                                    Map<String, Object> termStudentNum = new HashMap<>();
                                    termStudentNum.put(IDs.get(choose_term_index), "1");
                                    currentNumStudent = "0";
                                    ref2.set(termStudentNum);
                                }
                            } else {
                                Log.d("LOGGER", "get failed with ", task.getException());
                            }
                        }
                    });

                    /*currentNumStudent = Integer.parseInt(num)+1+"";
                    ref2.update(IDs.get(choose_term_index),currentNumStudent);*/

                    isr.close();
                    is.close();


                    //jsonFilter(jsonData);
                    /*try {


                    }
                    catch (JSONException e) {
                        Log.i("json：", "???" );
                        e.printStackTrace();
                    }
                    isr.close();
                    is.close();*/

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("https://us-central1-universityapp-10e74.cloudfunctions.net/numStudents?course="+CourseID);

    }

    /*
     * This method is used to add courses to the student's academic record
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     * @time 2019-3-15
     * */

    public void addCourse(){

        auth = FirebaseAuth.getInstance();
        final String authUid = auth.getUid();
        database = FirebaseFirestore.getInstance();

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
                    //String jsonDoc ="";
                    String line;
                    //bufferedReader.readLine();
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.i("Output：", "" + line);
                        jsonData = jsonData + line;
                    }
                    Log.i("json：", "" + jsonData);
                    bufferedReader.close();
                    //jsonFilter(jsonData);
                    try {
                        JSONObject jsonObject1 = new JSONObject(jsonData);
                        String stateus = jsonObject1.getString("status");
                        final String illustrate = jsonObject1.getString("illustrate");
                        Log.i("<--- stateus -->", stateus);
                        Log.i("<--- illustrate -->", illustrate);

                        if(stateus.equals("OK")){
                            DocumentReference ref = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document(CourseID);
                            ref.set(course);
                            renewCourseSpot();
                            //Add new term index
                            final DocumentReference userInstance = database.collection("User/").document(authUid);
                            userInstance.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        //String array = document.toString();
                                        if (document.exists()) {
                                            Log.d("LOGGER", "DocumentSnapshot data: " + document.getData());
                                            User thisUser = document.toObject(User.class);
                                            List<String> terms = thisUser.getTerms();
                                            if (terms.indexOf(IDs.get(choose_term_index)) < 0){
                                                terms.add(IDs.get(choose_term_index));
                                                thisUser.setTerms(terms);
                                            }
                                            Log.d("renew terms", terms.toString());
                                            userInstance.update("terms",terms);
                                        } /*else {
                                            Log.d("LOGGER", "No such document");
                                            DocumentReference docRef = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document("index");;
                                            List<String> newCourse = new ArrayList<>();
                                            newCourse.add(CourseID);
                                            SelectedIndex newlist = new SelectedIndex(newCourse);
                                            //docRef.set("{courses=["+docRef+"]}");
                                            docRef.set(newlist);
                                        }*/
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });

                            //renew array of index
                            DocumentReference docRef = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document("index");
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        if (document.exists()) {
                                            DocumentReference docRef = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document("index");
                                            Log.d("LOGGER", "DocumentSnapshot data: " + document.getData());
                                            SelectedIndex newlist = document.toObject(SelectedIndex.class);
                                            List<String> newCourse = newlist.returnTheArray();
                                            newCourse.add(CourseID);
                                            Log.d("newCourses", newCourse.toString());
                                            docRef.update("courses",newCourse);
                                        } else {
                                            Log.d("LOGGER", "No such document");
                                            DocumentReference docRef = database.collection("User/"+authUid+"/"+IDs.get(choose_term_index)).document("index");
                                            List<String> newCourse = new ArrayList<>();
                                            newCourse.add(CourseID);
                                            SelectedIndex newlist = new SelectedIndex(newCourse);
                                            Log.d("newCourses", newlist.toString());
                                            docRef.set(newlist);
                                        }
                                    } else {
                                        Log.d("LOGGER", "get failed with ", task.getException());
                                    }
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddCourseActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddCourseActivity.this, illustrate, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                    catch (JSONException e) {
                        Log.i("json：", "???" );
                        e.printStackTrace();
                    }
                    isr.close();
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute("http://app.indal.ca/api/selectCheck/?course="+CourseID+"&term="+IDs.get(choose_term_index)+"&uid="+auth.getUid());
    }

}