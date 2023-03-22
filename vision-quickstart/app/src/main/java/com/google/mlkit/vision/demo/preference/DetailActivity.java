package com.google.mlkit.vision.demo.preference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.java.CameraXLivePreviewActivity;

public class DetailActivity extends AppCompatActivity {

    private String name;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        String name = getIntent().getStringExtra("name");
        this.name = name;
        String url = "file:///android_asset/" + name + ".html";
        webView.loadUrl(url);
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void goBack() {
            ((Activity) mContext).finish();
        }

        @JavascriptInterface
        public void startCamera() {
            saveExerciseName(name);
            Intent intent = new Intent(mContext, CameraXLivePreviewActivity.class);
            mContext.startActivity(intent);
        }

        private void saveExerciseName(String exerciseName) {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("exercise_name", exerciseName);
            editor.apply();
        }
    }
}

