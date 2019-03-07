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

import com.google.firebase.firestore.DocumentReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;


public class AddCourseActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = 0;
    private String currentURL = "";
    private String cat = "";
    private String value = "";
    FloatingActionButton fab;
    private ArrayList<String> IDs = new ArrayList<String>();
    private ArrayList<String> terms = new ArrayList<String>();
    private AlertDialog alertDialog;


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
                value = "";
                if (currentURL.split("\\/").length > 5) {
                    cat = currentURL.split("\\/")[3];
                    value = currentURL.split("\\/")[5];
                }

                if (cat.equals("course")) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(AddCourseActivity.this);
                    builder.setTitle("确认");
                    builder.setMessage("Cat: " + cat + "\nValue: " + value);
                    builder.setPositiveButton("是", null);
                    builder.show();*/
                    fab.show();
                } else {
                    fab.hide();
                }

            }

        });
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        webView.loadUrl("http://app.indal.ca");          //调用loadUrl方法为WebView加入链接
    }


    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
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

    public void termSelection() {

        //final String items[] = {"我是Item一", "我是Item二", "我是Item三", "我是Item四"};
        final String items[] = (String[])terms.toArray(new String[terms.size()]);;
        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("Term Selection")//设置对话框的标题
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddCourseActivity.this, items[which], Toast.LENGTH_SHORT).show();
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
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}