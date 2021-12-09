package pro.app.fnl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class ConsoleActivity extends AppCompatActivity {
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    static RequestQueue requestQueue;
    static String regId;
    TextView call_txt, pow_txt, con_txt, temp_txt, coll_txt, fire_txt, gas_txt;
    ImageButton call_btn, move_console, move_controller, move_web,move_gallery, cap_btn;
    ToggleButton pow_btn, con_btn;
    LinearLayout container;
    NotificationManagerCompat notificationManager;
    String channelId = "channel";
    String channelName = "Channel_name";
    int importance = NotificationManager.IMPORTANCE_LOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        pow_btn = findViewById(R.id.pow_btn);
        call_btn = findViewById(R.id.call_btn);
        con_btn = findViewById(R.id.con_btn);
        cap_btn = findViewById(R.id.cap_btn);
        move_console = findViewById(R.id.move_console);
        move_controller = findViewById(R.id.move_controller);
        move_web = findViewById(R.id.move_web);
        move_gallery = findViewById(R.id.move_gallery);
        pow_txt = findViewById(R.id.pow_txt);
        call_txt = findViewById(R.id.call_txt);
        con_txt = findViewById(R.id.con_txt);
        temp_txt = findViewById(R.id.temp_txt);
        coll_txt = findViewById(R.id.coll_txt);
        gas_txt = findViewById(R.id.gas_txt);
        fire_txt = findViewById(R.id.fire_txt);
        container = findViewById(R.id.container);
        regId = "fUgb9-D3SlO3X3P9-1XgLV:APA91bEPOnZ_d62DGfewfOJug0_EjvCCLfLnfxAZRCxvDzErinXGKHa3QKgtZ5DsAV_GH72iLxS-DtjbJLH7_Zsgj3BhnKf9vMbB0aTpoapCUfSPqYRNvf7Ajk3shxamFtbDKxH79oA8";

        cap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               View rootview  = getWindow().getDecorView();
               File screenShot = ScreenShot(rootview);
               if(screenShot!=null) {
                   sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
               }
            }
            public File ScreenShot(View view) {
                view.setDrawingCacheEnabled(true);
                Bitmap screenBitmap = view.getDrawingCache();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
                Date currentTime_1 = new Date();
                String filename = formatter.format(currentTime_1)+".jpg";
                File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);
                FileOutputStream os = null;
                try{
                    os = new FileOutputStream(file);
                    screenBitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                    os.close();
                }catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                Toast.makeText(getApplicationContext(), filename + "저장", Toast.LENGTH_LONG).show();
                view.setDrawingCacheEnabled(false);
                return file;
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Main", "토큰 가져오는 데 실패함", task.getException());
                            return;
                        }
                        String newToken = task.getResult();
                        println("등록 id--------------------------------------- : " + newToken);
                    }
                });

        pow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300,5));
                }else {
                    vibrator.vibrate(300);
                }
                String input = "화재 발생 긴급 대피 요망  화재신고및 부상자 : call 119";
                Log.d("fcm", input);
                send(input);
            }
        });

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        move_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        move_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, ControllerActivity.class);
                startActivity(intent);
            }
        });

        move_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });

        con_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
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
    }

    void call() {
        if (ContextCompat.checkSelfPermission(ConsoleActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "911"));
            ConsoleActivity.this.startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(ConsoleActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
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

    public void println(String data) {
        Log.d("FMS", data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");
        if (intent != null) {
            processIntent(intent);
        }
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");
        if (from == null) {
            println("from is null.");
            return;
        }

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        println("title :"+title+"body : "+body);
        String channelId = "channel";

        notificationManager = NotificationManagerCompat.from(ConsoleActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent2 = new Intent(ConsoleActivity.this, ConsoleActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ConsoleActivity.this, 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ConsoleActivity.this, "channel")
                .setSmallIcon(R.drawable.fireman)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(0, mBuilder.build());
    }

    public void send (String input) {
     JSONObject requestData = new JSONObject();
     try{
         requestData.put("priority", "high");

         JSONObject dataobj = new JSONObject();
         dataobj.put("contents",input);
         requestData.put("data",dataobj);

         JSONArray idarray = new JSONArray();
         idarray.put(0, regId);
         requestData.put("registration_ids", idarray);
     } catch (Exception e) {
         e.printStackTrace();
     }

     sendData(requestData, new SendResponseListener() {
         @Override
         public void onRequestStarted() {
            println("started 호출");
         }

         @Override
         public void onRequestCompleted() {
            println("completed 호출");
         }

         @Override
         public void onRequestWithError(VolleyError error) {
            println("withError 호출");
         }
     });
    }
    public interface SendResponseListener {
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestWithError(VolleyError error);
    }
    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRequestWithError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return  params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key = AAAA1VVZLSw:APA91bFeZYPNf8ZCUYBVRcpM_XzeiDDR8k1hWujBXSPhalQcC_BknrVB3aHg_ijA5ryBSlk4-mwvjvBIu68nmkmc2-9LpuvADYX_2fxNPZZ8w5wCxtlGggj87B-Sg3z_94n0ayPj7Whx");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        request.setShouldCache(false);
        listener.onRequestStarted();
        requestQueue.add(request);
    }
}