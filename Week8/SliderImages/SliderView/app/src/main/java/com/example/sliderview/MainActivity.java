package com.example.sliderview;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SliderView sliderView;
    ArrayList<Integer> arrayList;
    SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderView = findViewById(R.id.imageSlider);

        arrayList = new ArrayList<>();
        arrayList.add(R.drawable.shopee1);
        arrayList.add(R.drawable.shopee2);
        arrayList.add(R.drawable.shopee3);
        arrayList.add(R.drawable.shopee4);

        sliderAdapter = new SliderAdapter(this, arrayList); // Sử dụng 'this' thay vì getApplicationContext()

        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.red));
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(5);
    }
}