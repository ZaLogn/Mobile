package com.example.sliderimages;



import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity {
    ViewFlipper viewFlipperMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_flipper);

        viewFlipperMain = findViewById(R.id.viewFlipperMain);
        ActionViewFlipperMain();
    }

    // Hàm Flipper
    private void ActionViewFlipperMain() {
        List<String> arrayListFlipper = new ArrayList<>();
        arrayListFlipper.add("https://hunufa.vn/wp-content/uploads/2024/10/hinh-anh-ly-cafe-highlands-2.webp");
        arrayListFlipper.add("https://thuytinhgiare.com/wp-content/uploads/2023/08/hinh-ly-cafe_30.jpg");
        arrayListFlipper.add("https://media-cdn.tripadvisor.com/media/photo-s/10/99/20/a7/black-milk-coffee-and.jpg");
        arrayListFlipper.add("https://thuytinhocean.net/wp-content/uploads/2023/07/hinh-anh-ly-ca-phe-muoi.jpg");

        for (int i = 0; i < arrayListFlipper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrayListFlipper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageView);
        }

        // Thiết lập thời gian chuyển đổi (3000ms = 3 giây)
        viewFlipperMain.setFlipInterval(3000);
        viewFlipperMain.setAutoStart(true);

        // Áp dụng animation
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperMain.setInAnimation(slide_in);
        viewFlipperMain.setOutAnimation(slide_out);
    }
}
