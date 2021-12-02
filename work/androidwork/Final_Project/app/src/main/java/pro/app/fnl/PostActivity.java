package pro.app.fnl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class PostActivity extends AppCompatActivity {
    Handler handler;
    Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(PostActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        handler = new Handler((Looper.myLooper()));
        handler.postDelayed(delayThread,2000);
    }
}