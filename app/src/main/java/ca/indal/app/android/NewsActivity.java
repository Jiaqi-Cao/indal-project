/*
 * @ author Yang Shu, Jessie Wang
 * @ version 2
 * @ time 3.8
 * */
package ca.indal.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class NewsActivity extends AppCompatActivity {

    private WebView webView1;
    private long exitTime = 0;
    private String currentURL = "";


    /*
     * This method build the webview of the news
     * @return nothing
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        webView1 = findViewById(R.id.webview2);
        webView1.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentURL = webView1.getUrl();


            }

        });
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.loadUrl("http://news.app.indal.ca");
    }


    /*
     * This method build the onBackPressed of the news
     * @return nothing
     * */

    @Override
    public void onBackPressed() {
        if (webView1.canGoBack()) {
            webView1.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "Try again for exit",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }

        }
    }


}
