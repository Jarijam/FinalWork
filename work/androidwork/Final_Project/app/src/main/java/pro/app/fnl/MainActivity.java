package pro.app.fnl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    /*Handler handler;*/
    String urlStr = "http://192.168.0.29:80/np/crddata.mc";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavi);
        /* handler = new Handler(Looper.myLooper());*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_control:
                        setFrag(0);
                        break;
                    case R.id.navigation_infor:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HttpRequest(urlStr);
                            }
                        }).start();
                        setFrag(1);
                        break;
                    case R.id.navigation_web:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        frag1=new Frag1();
        frag2=new Frag2();
        frag3=new Frag3();
        setFrag(2); // 첫 프래그먼트 화면 지정
    }
    public void HttpRequestBtn2(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequest(urlStr);
            }
        }).start();
    }

    public void HttpRequest(String urlStr){
        Log.d("erp","httprequest들어갔니?");
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Log.d("erp",con+"");
            if (con != null) {
                con.setRequestMethod("GET");
                con.setDoInput(true);
                Log.d("erp","con밑으로 들어갔니?");
                int resCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                Log.d("erp",in+"??");
                String JsonData = "";

                Log.d("erp",JsonData+"제이슨~");
                while (true) {
                    Log.d("erp","json읽기 들어왔니?");
                    Log.d("erp",JsonData);
                    JsonData = in.readLine();
                    if(JsonData == null){
                        break;
                    }
                    response.append(JsonData+"\n");
                }
                Log.d("erp",response.toString()+"");
                in.close();
                con.disconnect();

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame,frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.Main_Frame,frag3);
                ft.commit();
                break;


        }
    }
}