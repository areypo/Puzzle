package com.manytiles.p8;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        WebView helpWebView = findViewById(R.id.help_webview);
        helpWebView.setWebViewClient(new WebViewClient());
        helpWebView.loadDataWithBaseURL("file:///android_asset/", readAssetFileAsString(), "text/html", Xml.Encoding.UTF_8.name(), null);
    }

    public void onClickBackButton(View view) {
        finish();
    }

    private String readAssetFileAsString() {
        InputStream inputStream;
        String result = "";
        try {
            inputStream = getAssets().open("help.html");
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            result += inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
