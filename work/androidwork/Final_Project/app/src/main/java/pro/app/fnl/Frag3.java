package pro.app.fnl;

import static java.sql.DriverManager.println;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Frag3 extends Fragment  {
    private static final int CALL_PERMISSION_REQUEST_CODE = 1234;
    private View view;
    ViewGroup viewGroup;
    TextView call_txt, pow_txt, con_txt, sonar_txt;
    ImageView pow_img;
    ImageButton call_btn;
    ToggleButton pow_btn, con_btn;
    String channelId = "channel";
    String channelName = "Channel_name";
    NotificationManagerCompat notificationManager;
    int importance = NotificationManager.IMPORTANCE_LOW;

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
        sonar_txt = view.findViewById(R.id.sonar_txt);

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
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if(Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(300,5));
                }else {
                    vibrator.vibrate(300);
                }

                if (pow_btn.isChecked()) {
                    pow_txt.setText("ON");
                } else {
                    pow_txt.setText("OFF");
               }
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
                } else {
                    con_txt.setText("OFF");
                }
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
    public void println(String data1) {
        Log.d("FMS", data1);
    }


    private void processIntent(Intent intent) {
        String from = intent.getStringExtra("from");

        if (from == null) {
            println("from is null.");
            return;
        }

        String c1 = intent.getStringExtra("c1");
        String channelId = "channel";
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        Log.d("c1",c1);
        sonar_txt.setText(c1+"M" );
        
            Intent intent2 = new Intent(getContext(), Frag3.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), "channel")
                    .setSmallIcon(R.drawable.firefighter)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000});
            notificationManager = NotificationManagerCompat.from(getContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(0, mBuilder.build());

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

}

