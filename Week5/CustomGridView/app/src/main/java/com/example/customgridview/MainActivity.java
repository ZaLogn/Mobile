package com.example.customgridview;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến
    GridView gridView;
    ArrayList<MonHoc> arrayList;
    MonHocAdapter adapter;
    EditText editText1;
    Button btnNhap, btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần UI
        AnhXa();

        // Tạo Adapter và truyền dữ liệu vào ListView
        adapter = new MonHocAdapter(MainActivity.this, R.layout.row_monhoc, arrayList);
        gridView.setAdapter(adapter);
    }

    private void AnhXa() {
        gridView = findViewById(R.id.gridview1);
        editText1 = findViewById(R.id.editText1);
        btnNhap = findViewById(R.id.btnNhap);
        btnCapNhat = findViewById(R.id.btnCapNhat);

        // Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java", "Java 1", R.drawable.java1));
        arrayList.add(new MonHoc("C#", "C# 1", R.drawable.c));
        arrayList.add(new MonHoc("PHP", "PHP 1", R.drawable.php));
        arrayList.add(new MonHoc("Kotlin", "Kotlin 1", R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart", "Dart 1", R.drawable.dart));
    }
}
