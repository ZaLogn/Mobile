package com.example.gridview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private int vitri = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        GridView gridView;
        ArrayList<String> arrayList;
        //ánh xạ
        gridView = (GridView) findViewById(R.id.gridview1);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("C#");
        arrayList.add("PHP");
        arrayList.add("Kotlin");
        arrayList.add("Dart");
        //Tạo ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayList
        );
        //truyền dữ liệu từ adapter ra gridview
        gridView.setAdapter(adapter);
        //bắt sự kiện click nhanh trên từng dòng của Gridview
        EditText txt_text1 = findViewById(R.id.txt_text1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //code yêu cầu
                txt_text1.setText(arrayList.get(i));
                //i: trả về vị trí click chuột trên GridView -> i ban đầu =0
                Toast.makeText(MainActivity.this,"" + i, Toast.LENGTH_SHORT).show();
            }
        });

        EditText editText1;
        Button btnNhap;
        editText1 = (EditText) findViewById(R.id.txt_text1);
        btnNhap = (Button) findViewById(R.id.btnNhap);
        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText1.getText().toString();
                arrayList.add(name);
                adapter.notifyDataSetChanged();
            }
        });

        //Khai báo
        Button btnCapNhat;
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        //bắt sự kiện trên từng dòng của Gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lấy nội dung đua lên edittext
                editText1.setText(arrayList.get(i));
                vitri = i;
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.set(vitri, editText1.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Button btnXoa;
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xóa item
                arrayList.remove(vitri);
                adapter.notifyDataSetChanged();
            }
        });
    }
}