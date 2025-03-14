package com.example.recycleview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.Adapter.SongAdapter;
import com.example.recycleview.Model.SongModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvSongs;
    private SongAdapter mSongAdapter;
    private List<SongModel> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSongs = findViewById(R.id.rv_songs);

        // Create song data
        mSongs = new ArrayList<>();
        mSongs.add(new SongModel("60696", "NẾU EM CÒN TỒN TẠI", "Khi anh bắt đầu 1 tình yêu Là lúc anh tự thay", "Trịnh Đình Quang"));
        mSongs.add(new SongModel("60671", "NGỐC", "Có rất nhiều những câu chuyện Em dấu riêng mình em biết", "Khắc Việt"));
        mSongs.add(new SongModel("60650", "HÃY TIN ANH LẦN NỮA", "Dẫu cho ta đã sai khi ở bên nhau Có yêu thương", "Thiên Dũng"));
        mSongs.add(new SongModel("60616", "CHUỖI NGÀY VẮNG EM", "Từ khi em bước ra đi cõi lòng anh ngập tràng bao", "Duy Cường"));
        mSongs.add(new SongModel("60614", "KHI NGƯỜI MÌNH YÊU KHÓC", "Nước mắt em đang rơi trên những ngón tay Nước mắt em", "Phạm Mạnh Quỳnh"));
        mSongs.add(new SongModel("60608", "MỘNG", "Anh mơ gặp em anh mơ được ôm em", "Trịnh Thăng Bình"));
        mSongs.add(new SongModel("60752", "TÌNH YÊU CHẤP VÁ", "Muốn xa nơi xa yêu thương mình từng có để không nghe", "Mr. Siro"));
        mSongs.add(new SongModel("60758", "CHO NGÀY MƯA TAN", "1 ngày mưa và khuất xa nơi anh bằng đôi c", "Trung Đức"));
        mSongs.add(new SongModel("60759", "CÂU HỎI EM CHƯA TRẢ LỜI", "Cần nói em 1 lời giải thích thật lòng Đừng lặng im", "Yuki Huy Nam"));
        mSongs.add(new SongModel("60760", "QUA ĐI LẶNG LẼ", "Đôi khi đến với yêu thương chúng chẳng được lâu nhưng khi", "Phạm Mạnh Quỳnh"));
        mSongs.add(new SongModel("60761", "QUÊN ANH LÀ ĐIỀU EM KHÔNG THỂ - REMIX", "Cần thêm bao lâu để em quên đi niềm đau Cần thêm", "Thiên Ngôn"));

        // Initialize adapter
        mSongAdapter = new SongAdapter(this, mSongs);
        rvSongs.setAdapter(mSongAdapter);

        // Set LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
    }
}