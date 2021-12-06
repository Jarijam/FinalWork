package pro.app.fnl;

import static java.sql.DriverManager.println;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Frag3 extends Fragment  {
    String urlStr = "http://192.168.0.29:80/np/data.mc";
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    private View view;
    ViewGroup viewGroup;
    TextView call_txt, pow_txt, con_txt, temp_txt;
    ImageButton call_btn;
    ToggleButton pow_btn, con_btn;
    String sensorInfo;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.frag1,container,false);
        view = inflater.inflate(R.layout.frag3,container,false);
        pow_btn = view.findViewById(R.id.pow_btn);
        pow_txt = view.findViewById(R.id.pow_txt);
        call_txt = view.findViewById(R.id.call_txt);
        call_btn = view.findViewById(R.id.call_btn);
        con_btn = view.findViewById(R.id.con_btn);
        con_txt = view.findViewById(R.id.con_txt);
        temp_txt = view.findViewById(R.id.temp_txt);

        pow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300,5));
                }else {
                    vibrator.vibrate(300);
                }

                if (pow_btn.isChecked()) {
                    pow_txt.setText("ON");
                    Log.d("pow", "1");

                } else {
                    pow_txt.setText("OFF");
                    Log.d("pow", "0");
                   }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = " ";
                        if(pow_btn.isChecked()){
                            msg = "ON" ;
                        }else {
                            msg = "OFF" ;
                        }
                        try {
                            signUPHttp2(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

        con_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300,5));
                }else {
                    vibrator.vibrate(300);
                }
                if (con_btn.isChecked()) {
                    con_txt.setText("ON");
                    Log.d("con", "1");
                } else {
                    con_txt.setText("OFF");
                    Log.d("con", "0");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = " ";
                        if(con_btn.isChecked()){
                            msg = "Wireless" ;
                        }else {
                            msg = "flowinglines" ;
                        }
                        try {
                            signUPHttp(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               call();
            }
        });
        return view;

    }

    void call() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "911"));
            getActivity().startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    public void signUPHttp(String SensorInfo) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.0.29:80/np/androidmode.mc");
        ArrayList<NameValuePair> sensorInfo = new ArrayList<NameValuePair>();
        try {
            sensorInfo.add(new BasicNameValuePair("con_txt", URLDecoder.decode(SensorInfo, "UTF-8")));
            post.setEntity(new UrlEncodedFormEntity(sensorInfo, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.d("signUp", ex.toString());
        }
        HttpResponse response = client.execute(post);
        Log.d("signUp", "response StatusCode:"+response.getStatusLine().getStatusCode()); // response StatusCode: 200
    }

    public void signUPHttp2(String SensorInfo2) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.0.29:80/np/androidpower.mc");
        ArrayList<NameValuePair> sensorInfo2 = new ArrayList<NameValuePair>();
        try {
            sensorInfo2.add(new BasicNameValuePair("pow_txt", URLDecoder.decode(SensorInfo2, "UTF-8")));
            post.setEntity(new UrlEncodedFormEntity(sensorInfo2, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.d("signUp", ex.toString());
        }
        HttpResponse response = client.execute(post);

        Log.d("signUp", "response StatusCode:"+response.getStatusLine().getStatusCode()); // response StatusCode: 200
    }
}

