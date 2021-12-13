package pro.app.fnl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ControllerActivity extends AppCompatActivity {
    String TAG = "BluetoothActivity";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    ImageButton move_console, move_controller, move_web, move_gallery, con_up, con_down, con_left, con_right, con_break, con_acell, blue_btn, chn_mode;
    TextView blue_con, chn_txt;
    ListView blue_name;
    WebView cam;
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;
    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        move_console = findViewById(R.id.move_console);
        move_controller = findViewById(R.id.move_controller);
        move_web = findViewById(R.id.move_web);
        move_gallery = findViewById(R.id.move_gallery);
        cam = findViewById(R.id.cam);
        con_up = findViewById(R.id.up_btn);
        con_down = findViewById(R.id.con_down);
        con_left = findViewById(R.id.con_left);
        con_right = findViewById(R.id.con_right);
        con_break = findViewById(R.id.con_break);
        con_acell = findViewById(R.id.con_acell);
        blue_btn = findViewById(R.id.blue_btn);
        blue_con = findViewById(R.id.blue_con);
        blue_name = findViewById(R.id.blue_name);
        chn_txt = findViewById(R.id.chn_txt);

        //webView
        WebSettings webSettings = cam.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        cam.setWebViewClient(new WebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSaveFormData(false);
        cam.loadUrl("https://youtu.be/9ep19RD3E6I");

        //bluetooth
        String[] permission_list = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(ControllerActivity.this, permission_list, 1);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        blue_name.setAdapter(btArrayAdapter);
        blue_name.setOnItemClickListener(new myOnItemClickListener());

        //intent - gallery
        move_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        //intent - console
        move_console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerActivity.this, ConsoleActivity.class);
                startActivity(intent);
            }
        });

        //intent - web
        move_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControllerActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });
    }

    //bluetooth - connected
    public void onClickButtonPaired(View view){
        btArrayAdapter.clear();
        if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }

    //bluetooth - send data
    public void onClickButtonup(View view){
        if(connectedThread!=null){ connectedThread.write("F"); }
    }

    public void onClickButtondown(View view){
        if(connectedThread!=null){ connectedThread.write("B"); }
    }

    public void onClickButtonleft(View view){
        if(connectedThread!=null){ connectedThread.write("L"); }
    }

    public void onClickButtonright(View view){
        if(connectedThread!=null){ connectedThread.write("R"); }
    }

    public void onClickButtonbreak(View view){
        if(connectedThread!=null){ connectedThread.write("S"); }
    }

    public void onClickButtonacell(View view){
        if(connectedThread!=null){ connectedThread.write("C"); }
    }

    public void chn_mode(View view){
        if(connectedThread!=null){ connectedThread.write("Z"); }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class myOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            blue_con.setText("try...");
            final String name = btArrayAdapter.getItem(position);
            final String address = deviceAddressArray.get(position);
            boolean flag = true;

            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            try {
                btSocket = createBluetoothSocket(device);
                btSocket.connect();
            } catch (IOException e) {
                flag = false;
                blue_con.setText("failed!");
                e.printStackTrace();
            }

            if(flag){
                blue_con.setText("connected");
                connectedThread = new ConnectedThread(btSocket);
                connectedThread.start();
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

    private class ViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            view.loadUrl(url);
            return true;
        }
    }
}