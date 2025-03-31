package com.example.circleindicator3andviewpager2;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import me.relex.circleindicator.CircleIndicator3;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;
    private Handler handler = new Handler();
    private Runnable runnable;
    private static final long DELAY = 3000; // 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewpager2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);

        imagesList1 = getListImages();
        ImagesViewPager2Adapter adapter = new ImagesViewPager2Adapter(imagesList1);
        viewPager2.setAdapter(adapter);

        circleIndicator3.setViewPager(viewPager2);

        // Tạo runnable để tự động chuyển trang
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                if (currentItem == imagesList1.size() - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(currentItem + 1);
                }
                handler.postDelayed(this, DELAY);
            }
        };

        // Lắng nghe sự kiện chuyển trang
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, DELAY);
            }
        });

        // Bắt đầu auto-scroll
        handler.postDelayed(runnable, DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private List<Images> getListImages() {
        List<Images> list = new ArrayList<>();
        list.add(new Images(R.drawable.quangcao));
        list.add(new Images(R.drawable.coffee));
        list.add(new Images(R.drawable.companypizza));
        list.add(new Images(R.drawable.themoingon));
        return list;
    }
}
