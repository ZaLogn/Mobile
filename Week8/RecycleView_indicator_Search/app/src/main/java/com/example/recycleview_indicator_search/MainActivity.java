package com.example.recycleview_indicator_search;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcIcon;
    private List<IconModel> arrayList1;
    private IconAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo RecyclerView
        rcIcon = findViewById(R.id.rcIcon);

        // Tạo danh sách dữ liệu
        arrayList1 = new ArrayList<>();
        arrayList1.add(new IconModel(R.drawable.icon1, "jfdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon2, "sdfdf sfdsf"));
        arrayList1.add(new IconModel(R.drawable.icon3, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon4, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon5, "jfdjfdjf djfdh"));
        arrayList1.add(new IconModel(R.drawable.icon6, "sdfdf sfdsf"));
        arrayList1.add(new IconModel(R.drawable.icon7, "sdfdf sfds"));
        arrayList1.add(new IconModel(R.drawable.icon8, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon9, "dfgfhyh sxdff"));
        arrayList1.add(new IconModel(R.drawable.icon10, "jfdjfdjf djfdh"));

        // Cài đặt LayoutManager cho RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(gridLayoutManager);

        // Cài đặt Adapter cho RecyclerView
        iconAdapter = new IconAdapter(getApplicationContext(), arrayList1);
        rcIcon.setAdapter(iconAdapter);

        // Khởi tạo SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListener(newText);
                return true;
            }
        });
    }

    private void filterListener(String text) {
        List<IconModel> list = new ArrayList<>();
        for (IconModel iconModel : arrayList1) {
            if (iconModel.getDesc().toLowerCase().contains(text.toLowerCase())) {
                list.add(iconModel);
            }
        }
        if (list.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setListenerList(list);
        }
    }
}