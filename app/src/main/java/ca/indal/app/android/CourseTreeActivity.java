/*
 * @author Xuemin Yu, Jiaqi Cao
 * @version 1
 * @time: 3.22
 * */

package ca.indal.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CourseTreeActivity extends AppCompatActivity {

   /* private Button mathbtn, commbtn, cscibtn;
    private ImageView view;*/

    private WebView webview5;
    private long exitTime = 0;
    private String currentURL = "";

    /*
     * This method build the interface of Course Tree Activity
     * add the important date webview
     * @return Nothing
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_tree);

       /* mathbtn = findViewById(R.id.math);
        commbtn = findViewById(R.id.comm);
        cscibtn = findViewById(R.id.csci);

        view = findViewById(R.id.imageView);
        mathbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.math);
            }
        });

        commbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.comm);
            }
        });

        cscibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setImageResource(R.drawable.cs);
            }
        });*/

        webview5 = findViewById(R.id.webview5);
        webview5.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentURL = webview5.getUrl();
            }

        });
        webview5.getSettings().setJavaScriptEnabled(true);
        webview5.loadUrl("http://news.app.indal.ca/course-tree/");
    }

    /*
     * This method build the go back button
     * @return Nothing.
     * */
    @Override
    public void onBackPressed() {
        if (webview5.canGoBack()) {
            webview5.goBack();
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
}
