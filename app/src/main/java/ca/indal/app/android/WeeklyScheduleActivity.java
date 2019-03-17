/*
 * @author Xuemin Yu, Jinkun Chen
 * @version 1
 * @time:3.16
 * */

package ca.indal.app.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private ArrayList<String> terms = new ArrayList<String>();
    private ArrayList<String> IDs = new ArrayList<String>();
    private AlertDialog alertDialog;
    int choose_term_index = 0;
    private FirebaseAuth auth;
    private FirebaseFirestore database;

    private WebView webview4;
    private long exitTime = 0;
    private String currentURL = "";


    /*
     * This method build the interface of Weekly Schedule Activity
     * add the Weekly Schedule webview
     * @return Nothing
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeklyschedule);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        final String authUid = auth.getUid();

        readCSV();
    }

    /*
     * This method build the go back button for Add and Drop Activity
     * @return Nothing.
     * */
    @Override
    public void onBackPressed() {
        if (webview4.canGoBack()) {
            webview4.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "try again to exit",
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            termSelection();

                        }
                    });
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
     * This method is used to create an dialog for user to choose term before showing the user's weekly schedule
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
                        Toast.makeText(WeeklyScheduleActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            //Toast.makeText(WeeklyScheduleActivity.this, items[which] + "'s Weekly Schedule", Toast.LENGTH_SHORT).show();

                        webview4 = findViewById(R.id.webview4);
                        webview4.setWebViewClient(new WebViewClient() {

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                currentURL = webview4.getUrl();
                            }

                        });
                        webview4.getSettings().setJavaScriptEnabled(true);
                        webview4.loadUrl("http://app.indal.ca/api/timetable/?uid=" + authUid + "&term=" + IDs.get(choose_term_index));

                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}

