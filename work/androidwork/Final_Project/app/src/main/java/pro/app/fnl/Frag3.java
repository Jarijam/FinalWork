package pro.app.fnl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Frag3 extends Fragment  {
    private View view;
    ViewGroup viewGroup;
    TextView call_txt, pow_txt;
    ImageButton call_btn, pow_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.frag1,container,false);
        view = inflater.inflate(R.layout.frag3,container,false);
        pow_txt = view.findViewById(R.id.pow_txt);
        pow_btn = view.findViewById(R.id.pow_btn);
        call_txt = view.findViewById(R.id.call_txt);
        call_btn = view.findViewById(R.id.call_btn);


        pow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pow_txt.setText("OFF");

            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("119"));
                startActivity(intent);
                } catch (Exception e) {

                }
            }
        });
        return view;
    }

}