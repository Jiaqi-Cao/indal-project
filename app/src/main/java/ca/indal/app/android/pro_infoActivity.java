/*
 * @author Xuemin Yu, Jiaqi Cao
 * @version 1
 * @time:3.22
 * */
package ca.indal.app.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class pro_infoActivity extends AppCompatActivity {

    private WebView webview4;
    private long exitTime = 0;
    private String currentURL = "";


    /*
     * This method build the interface of Professor information Activity
     * add the important date webview
     * @return Nothing
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_info);


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
        webview4.loadUrl("http://prof.app.indal.ca/");
    }

    /*
     * This method build the go back button
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
}
