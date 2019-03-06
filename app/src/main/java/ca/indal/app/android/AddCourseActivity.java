package ca.indal.app.android;


import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;

import ca.indal.app.android.model.Course;

public class AddCourseActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = 0;
    private String currentURL = "";
    private String cat = "";
    private String value = "";
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        fab = findViewById(R.id.fab);

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
                if (currentURL.split("\\/").length > 5){
                    cat = currentURL.split("\\/")[3];
                    value = currentURL.split("\\/")[5];
                }

                if (cat.equals("course")){
                    AlertDialog.Builder builder  = new AlertDialog.Builder(AddCourseActivity.this);
                    builder.setTitle("确认" ) ;
                    builder.setMessage("Cat: " + cat + "\nValue: "+ value) ;
                    builder.setPositiveButton("是" ,  null );
                    builder.show();
                    fab.show();
                }
                else{
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
}