package pro.app.fnl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothSocket;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConsoleActivity extends AppCompatActivity {

    String urlStr = "http://192.168.0.29:80/np/data.mc";

    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    static RequestQueue requestQueue;
    static String regId, regId2;
    TextView call_txt, pow_txt, temp_txt, fire_txt, gas_txt;
    ImageButton call_btn, move_console, move_controller, move_web,move_gallery, cap_btn;
    Button data_on, data_off, btn_map;
    ImageView fire_img, gas_img, temp_img, data_img;
    ToggleButton pow_btn;
    LinearLayout container;
    NotificationManagerCompat notificationManager;
    String channelId = "channel";
    String channelName = "Channel_name";
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;
    int importance = NotificationManager.IMPORTANCE_LOW;
    private static MyAsynch myAsynch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        data_on = findViewById(R.id.data_on);
        data_off = findViewById(R.id.data_off);
        pow_btn = findViewById(R.id.pow_btn);
        call_btn = findViewById(R.id.call_btn);
        cap_btn = findViewById(R.id.cap_btn);
        btn_map = findViewById(R.id.btn_map);
        move_console = findViewById(R.id.move_console);
        move_controller = findViewById(R.id.move_controller);
        move_web = findViewById(R.id.move_web);
        move_gallery = findViewById(R.id.move_gallery);
        pow_txt = findViewById(R.id.pow_txt);
        call_txt = findViewById(R.id.call_txt);
        temp_txt = findViewById(R.id.temp_txt);
        gas_txt = findViewById(R.id.gas_txt);
        fire_txt = findViewById(R.id.fire_txt);
        temp_img = findViewById(R.id.temp_img);
        gas_img = findViewById(R.id.gas_img);
        fire_img = findViewById(R.id.fire_img);
        data_img = findViewById(R.id.data_img);
        container = findViewById(R.id.container);
        regId = "fUgb9-D3SlO3X3P9-1XgLV:APA91bEPOnZ_d62DGfewfOJug0_EjvCCLfLnfxAZRCxvDzErinXGKHa3QKgtZ5DsAV_GH72iLxS-DtjbJLH7_Zsgj3BhnKf9vMbB0aTpoapCUfSPqYRNvf7Ajk3shxamFtbDKxH79oA8";
        regId2 = "c1UI3eBjTtupybel2GeJFT:APA91bEcRXROZl-lGzDXls5WHbru8fuPw92lVt5V4SQ_W7YO-MSYpwrpVw3KcWQAiVCV1WA1CY8M7mUe4hs62LYkmUDxaSlFJG8ds2xxUS3QR3x_3tZwreOTyUfBWvKsLfh2GKcvlvcg";

        // screenshot
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

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        //FCM
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

        //FCM - send
        pow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300,5));
                }else {
                    vibrator.vibrate(300);
                }
                String input = "화재 발생 긴급 대피   <화재신고 및 부상자 : 119 >";
                Log.d("fcm", input);
                send(input);
            }
        });

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        //intent-gallery
        move_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        //intent-controller
        move_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, ControllerActivity.class);
                startActivity(intent);
            }
        });

        //intent-web
        move_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsoleActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });

        //call
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
    //data receive
    public void data_on (View v){
        String url = "http://192.168.0.29/np/androidtemp.mc";

        myAsynch = new MyAsynch();
        myAsynch.execute(url);
        data_on.setEnabled(false);
        data_off.setEnabled(true);
        Log.d("park","시작?");
        }

    public void data_off(View v) {
        myAsynch.cancel(true);
        myAsynch.onCancelled();
    }

     class MyAsynch extends AsyncTask<String, String, Void> {

         @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0].toString();
            while (true) {
                if (isCancelled() == true) {
                    break;
                }
                String result = HttpConnect.getString(url);
                onProgressUpdate(result);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final String... values) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(Integer.parseInt(values[0].split(",")[1]) >= 23) {
                                temp_txt.setText(values[0].split(",")[1]+"℃");
                                temp_img.setImageResource(R.drawable.temp2);
                            }else if(Integer.parseInt(values[0].split(",")[1]) >= 30) {
                                temp_txt.setText(values[0].split(",")[1]+"℃");
                                temp_img.setImageResource(R.drawable.temp3);
                            }else {
                                temp_txt.setText(values[0].split(",")[1]+"℃");
                                temp_img.setImageResource(R.drawable.temp);
                            }

                            if(values[0].split(",")[0].equals("0")) {
                                gas_txt.setText(values[0].split(",")[0]+"ppm");
                                gas_img.setImageResource(R.drawable.gassensor2);
                            }else {
                                gas_txt.setText(values[0].split(",")[0]+"ppm");
                                gas_img.setImageResource(R.drawable.gassensor);
                            }

                            if(values[0].split(",")[2].equals("0")) {
                                fire_txt.setText("SAFE");
                                fire_img.setImageResource(R.drawable.firesensor2);
                            }else {
                                fire_txt.setText("WARNING!!!!");
                                fire_img.setImageResource(R.drawable.firesensor);
                            }
                        }
                    });
                }
            }).start();
        }

        @Override
        protected void onCancelled() {
            data_on.setEnabled(true);
            data_off.setEnabled(false);
        }
    }

    //FCM - notification
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
        String contents = intent.getStringExtra("contents");

        println("title :"+title+"body : "+body+"contents : "+contents);
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
                .setContentText(contents)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(0, mBuilder.build());
    }

    //FCM - send
    public void send (String input) {
     JSONObject requestData = new JSONObject();
     try{
         requestData.put("priority", "high");

         JSONObject dataobj = new JSONObject();
         dataobj.put("contents",input);
         requestData.put("data",dataobj);

         JSONArray idarray = new JSONArray();
         idarray.put(0, regId);
         idarray.put(1, regId2);

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