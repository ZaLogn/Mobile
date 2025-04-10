package com.example.autoloadactivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; // Dùng AppCompatActivity thay vì Activity

public class HomeActivity extends AppCompatActivity { // Kế thừa AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Đảm bảo file activity_home.xml tồn tại
    }
}
