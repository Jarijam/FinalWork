package pro.app.fnl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FMS";
    NotificationManagerCompat notificationManager;
    private static SimpleDateFormat format = new SimpleDateFormat("HH시 mm분");

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "onNewToken 호출됨 : " + token);
    }

    @Override
    public void onMessageReceived(@NonNull  RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived 호출됨.");

        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
       // String contents1 = data.get("c1");
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Log.d(TAG, "from : " + from +", title : "+title+", body : "+ body);
        sendToActivity(getApplicationContext(), from, title, body);
    }

    private void sendToActivity(Context context, String from,String title, String body) {
        Intent intent = new Intent(context, ConsoleActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        String dateStr = format.format(new Date());
        intent.putExtra("date", dateStr);
        //intent.putExtra("c1",contents1);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
