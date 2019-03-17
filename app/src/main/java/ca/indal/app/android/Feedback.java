package ca.indal.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    private WebView webView2;
    private long exitTime = 0;
    private String currentURL = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);


        webView2 = findViewById(R.id.webview3);
        webView2.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view1, String url) {
                view1.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view1, String url) {
                super.onPageFinished(view1, url);
                currentURL = webView2.getUrl();


            }

        });
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.loadUrl("http://news.app.indal.ca/feedback/");
    }

    @Override
    public void onBackPressed() {
        if (webView2.canGoBack()) {
            webView2.goBack();
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