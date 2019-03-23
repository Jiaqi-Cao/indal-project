/*
 * @author Xuemin Yu
 * @version 1
 * @time:3.15
 * */
package ca.indal.app.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ImportantDatesActivity extends AppCompatActivity {

    private WebView webview3;
    private long exitTime = 0;
    private String currentURL = "";


    /*
     * This method build the interface of Important Date Activity
     * add the important date webview
     * @return Nothing
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importantdates);


        webview3 = findViewById(R.id.webview3);
        webview3.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentURL = webview3.getUrl();
            }

        });
        webview3.getSettings().setJavaScriptEnabled(true);
        webview3.loadUrl("http://news.app.indal.ca/important-dates/");
    }

    /*
     * This method build the go back button for Add and Drop Activity
     * @return Nothing.
     * */
    @Override
    public void onBackPressed() {
        if (webview3.canGoBack()) {
            webview3.goBack();
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
