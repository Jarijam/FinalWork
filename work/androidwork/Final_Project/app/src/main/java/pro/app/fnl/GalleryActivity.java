package pro.app.fnl;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    ImageButton move_console, move_controller, move_web;
    TextView textView;
    RecyclerView recyclerView;
    PictureAdapter adapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int pictureCount = 0;
    ArrayList<PictureInfo> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PictureAdapter();
        recyclerView.setAdapter(adapter);
        move_console = findViewById(R.id.move_console);
        move_controller = findViewById(R.id.move_controller);
        move_web = findViewById(R.id.move_web);

        move_console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, ConsoleActivity.class);
                startActivity(intent);
            }
        });

        move_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, ControllerActivity.class);
                startActivity(intent);
            }
        });

        move_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new OnPictureItemClickListener() {
            @Override
            public void onItemClick(PictureAdapter.ViewHolder holder, View view, int position) {
                PictureInfo item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getDisplayName(), Toast.LENGTH_LONG).show();
            }
        });

        ArrayList<PictureInfo> result = queryAllPictures();
        adapter.setItems(result);
        adapter.notifyDataSetChanged();
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부된 권한 갯수 : " + permissions.size());
                    }
                })
                .start();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private ArrayList<PictureInfo> queryAllPictures() {
        ArrayList<PictureInfo> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        pictureCount = 0;
        while (cursor.moveToNext()) {
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));

            if (!TextUtils.isEmpty(path)) {
                PictureInfo info = new PictureInfo(path, displayName, addedDate);
                result.add(info);
            }
            pictureCount++;
        }

        textView.setText(pictureCount + " 개");
        Log.d("GalleryActivity", "Picture count : " + pictureCount);
        for (PictureInfo info : result) {
            Log.d("GalleryActivity", info.toString());
        }
        return result;
    }

}
