package pro.app.fnl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

public class testActivity extends AppCompatActivity {
    String btn;
    String gas;
    String flame;
    String dis;


    Handler handler;
//    String urlStr = "http://192.168.0.25:8088/bigdataERP/product/show_json";
    String urlStr = "http://192.168.0.29:80/np/crddata.mc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        handler = new Handler(Looper.myLooper());
    }
    public void HttpRequestBtn(View v){
        HttpPermissionChecker(v);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpRequest(urlStr);
            }
        }).start();
    }
    public void HttpPermissionChecker(View V){
        int chk = PermissionChecker.checkSelfPermission(this,Manifest.permission.INTERNET);
        if(chk==PackageManager.PERMISSION_GRANTED){
            Log.d("erp","성공");
            Intent intent = new Intent(testActivity.this, PostActivity.class);
            startActivity(intent);
            Toast.makeText(this,"요청 성공! Logcat을 확인해 주세요!",Toast.LENGTH_LONG).show();
        }else {
            Log.d("erp","실패");
            Toast.makeText(this,"요청 실패",Toast.LENGTH_LONG).show();
            return;
        }
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
    }
