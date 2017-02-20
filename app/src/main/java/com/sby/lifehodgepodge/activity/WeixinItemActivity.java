package com.sby.lifehodgepodge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sby.lifehodgepodge.R;

public class WeixinItemActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_item);
        webView= (WebView) findViewById(R.id.wx_webview);
        //获取intenturl
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        //设置webview，做这个时候超链接点不了，照网上说的重新设置了一下setting，就行了
        initwebview(url);
    }

    private void initwebview(String url){
        //照网上的设置
        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
//      s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);
        webView.requestFocus();
//      webView.setScrollBarStyle(0);

        //设置此后 在webview中点开另一个网页，仍然用webview显示，而非系统浏览器
        webView.setWebViewClient(new mywebviewClient());
        webView.loadUrl(url);
    }

    //重写onkeydown，back键时会返回上一页而不是直接退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        finish();
        return true;
    }

    private class mywebviewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url=request.getUrl().toString();
            view.loadUrl(url);
            return true;
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.i("shole", "shouldOverrideUrlLoading: ");
//            view.loadUrl(url);
//            return true;
//        }


    }
}
