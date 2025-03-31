//22110367_PhungGiaLong
package com.example.ui_loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private ImageButton btnLogin;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ View
        initViews();

        // Khởi tạo APIService
        apiService = RetrofitClient.getClient().create(APIService.class);

        // Xử lý sự kiện Click bằng Lambda
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<User> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    // Hiển thị thông báo giống như cách bạn yêu cầu
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công! Chào " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    handleLoginError(response);
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                Log.e("LOGIN_ERROR", "Lỗi: " + t.getMessage());
            }
        });
    }

    private void handleLoginError(Response<User> response) {
        try {
            String errorBody = response.errorBody().string();
            JSONObject jsonObject = new JSONObject(errorBody);
            String errorMessage = jsonObject.optString("message", "Đăng nhập thất bại!");
            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
        }
    }
}
