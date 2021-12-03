package pro.app.fnl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RequestActivity extends AppCompatActivity {
    Frag3 frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        frag = new Frag3();

        Intent intent = getIntent();
        String c = intent.getStringExtra("c1");
        getSupportFragmentManager().beginTransaction().replace(R.id.requert, frag).commit();
        Bundle bundle = new Bundle();
        bundle.putString("c1",c);
        frag.setArguments(bundle);

    }
}