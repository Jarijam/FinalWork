package pro.app.fnl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Frag3 extends Fragment  {
    private View view;
    ViewGroup viewGroup;
    ImageView fan_img;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.frag1,container,false);
        view = inflater.inflate(R.layout.frag3,container,false);
        fan_img = (ImageView)viewGroup.findViewById(R.id.fan_img);

        return view;
    }
        public void setImage(int index) {
            if (index == 0) {
                 fan_img.setImageResource(R.drawable.fan0);
            } else if (index == 1) {
                 fan_img.setImageResource(R.drawable.fan1);
            }
        }
}