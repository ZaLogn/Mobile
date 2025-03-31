package com.example.flagment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flagment.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

// MainActivity hiển thị một ViewPager2 với các tab và một menu trên Toolbar
public class MainActivity extends AppCompatActivity {

    // View Binding cho layout activity_main.xml
    private ActivityMainBinding binding;
    // Adapter cho ViewPager2
    private ViewPager2Adapter viewPager2Adapter;
    // Handler để xử lý trì hoãn thay đổi icon của FAB
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ẩn thanh trạng thái và thanh điều hướng để hiển thị toàn màn hình
        setupFullScreenMode();

        // Thiết lập Toolbar
        setSupportActionBar(binding.toolbar);

        // Thiết lập FloatingActionButton (FAB)
        setupFloatingActionButton();

        // Thiết lập TabLayout và ViewPager2
        setupTabLayoutAndViewPager();
    }

    // Thiết lập chế độ toàn màn hình
    private void setupFullScreenMode() {
        getWindow().setDecorFitsSystemWindows(false);
        WindowInsetsController controller = getWindow().getInsetsController();
        controller.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    // Thiết lập FloatingActionButton và xử lý sự kiện click
    private void setupFloatingActionButton() {
        FloatingActionButton fab = binding.fabAction;
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        });
    }

    // Thiết lập TabLayout và ViewPager2, liên kết chúng với nhau
    private void setupTabLayoutAndViewPager() {
        // Thêm các tab vào TabLayout
        String[] tabTitles = {"Xác nhận", "Lấy hàng", "Đang giao", "Đánh giá", "Hủy"};
        for (String title : tabTitles) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title));
        }

        // Thiết lập ViewPager2
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager2Adapter = new ViewPager2Adapter(fragmentManager, getLifecycle());
        binding.viewPager2.setAdapter(viewPager2Adapter);

        // Liên kết TabLayout với ViewPager2
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
                changeFabIcon(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý
            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    private void changeFabIcon(int index) {
        binding.fabAction.hide();
        handler.postDelayed(() -> {
            int iconResId;
            switch (index) {
                case 0:
                    iconResId = R.drawable.ic_baseline_chat_24;
                    break;
                case 1:
                    iconResId = R.drawable.ic_baseline_camera_alt_24;
                    break;
                case 2:
                    iconResId = R.drawable.ic_baseline_phone_24;
                    break;
                default:
                    // Giữ icon mặc định cho các tab khác
                    binding.fabAction.show();
                    return;
            }
            binding.fabAction.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, iconResId));
            binding.fabAction.show();
        }, 500); // Trì hoãn 2 giây
    }

    // Tạo menu trên Toolbar (sử dụng menu_main.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Xử lý sự kiện khi người dùng chọn một mục trong menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Lấy ID của menu item được chọn
        int id = item.getItemId();
        String message;

        // So sánh ID với các giá trị R.id.menu...
        if (id == R.id.menuSearch) {
            message = "Bạn đang chọn Search";
        } else if (id == R.id.menuNewGroup) {
            message = "Bạn đang chọn New Group";
        } else if (id == R.id.menuBroadcast) {
            message = "Bạn đang chọn New Broadcast";
        } else if (id == R.id.menuWeb) {
            message = "Bạn đang chọn WhatsApp Web";
        } else if (id == R.id.menuMessage) {
            message = "Bạn đang chọn Starred Messages";
        } else if (id == R.id.menuSetting) {
            message = "Bạn đang chọn Settings";
        } else {
            return super.onOptionsItemSelected(item);
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }
}