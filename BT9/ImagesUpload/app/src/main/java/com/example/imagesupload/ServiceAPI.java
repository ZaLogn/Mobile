package com.example.imagesupload;

import android.os.Message;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface ServiceAPI {
    // Định nghĩa URL API
    public static final String BASE_URL = "http://app.iotstar.vn:8081/appfoods/";

    // Khởi tạo Gson để xử lý dữ liệu ngày tháng
    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    // Tạo Retrofit Client
    ServiceAPI serviceapi = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServiceAPI.class);

    // API upload ảnh với danh sách hình ảnh
    @Multipart
    @POST("upload.php")
    Call<List<ImageUpload>> upload(@Part(Const.MY_USERNAME) RequestBody username,
                                   @Part MultipartBody.Part avatar);

    // API upload ảnh với phản hồi dạng Message
    @Multipart
    @POST("upload1.php")
    Call<Message> upload1(@Part(Const.MY_USERNAME) RequestBody username,
                          @Part MultipartBody.Part avatar);
}
