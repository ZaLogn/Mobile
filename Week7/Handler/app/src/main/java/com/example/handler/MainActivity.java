package com.example.handler;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> rowsArrayList = new ArrayList<>();
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        populateData();
        initAdapter();
        initScrollListener();
    }
    private void populateData() {
        int i = 0;
        while (i < 10) {
            rowsArrayList.add("Item " + i);
            i++;
        }
    }
    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter (rowsArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager (new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter (recyclerViewAdapter);
    }
    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged (recyclerView, newState);
            }
            @Override
            public void onScrolled (@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled (recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    if (!isLoading && gridLayoutManager.findLastCompletelyVisibleItemPosition() == rowsArrayList.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }
    private void loadMore() {
        rowsArrayList.add("loading"); // Thay null bằng chuỗi để tránh lỗi Adapter
        recyclerViewAdapter.notifyItemInserted(rowsArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rowsArrayList.remove(rowsArrayList.size() - 1); // Xóa loading indicator
                recyclerViewAdapter.notifyItemRemoved(rowsArrayList.size());

                int scrollPosition = rowsArrayList.size();
                int nextLimit = scrollPosition + 10;

                while (scrollPosition < nextLimit + 10) {
                    rowsArrayList.add("Item " + scrollPosition);
                    scrollPosition++;
                }

                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false; // ✅ Cập nhật lại isLoading
            }
        }, 2000);
    }
}