package pro.app.fnl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Console;

public class WebActivity extends AppCompatActivity {
    WebView web;
    EditText text;
    Button conn;
    ImageButton move_console, move_controller, move_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        web =  findViewById(R.id.webview);
        conn = findViewById(R.id.web_id);
        text = findViewById(R.id.web_text);
        move_console = findViewById(R.id.move_console);
        move_controller = findViewById(R.id.move_controller);
        move_web = findViewById(R.id.move_web);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSaveFormData(false);
        web.loadUrl("http://naver.com");

        move_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebActivity.this, ControllerActivity.class);
                startActivity(intent);
            }
        });

        move_console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebActivity.this, ConsoleActivity.class);
                startActivity(intent);
            }
        });

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl(text.getText().toString());
            }
        });
    }
    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            view.loadUrl(url);
            return true;
        }
    }
}