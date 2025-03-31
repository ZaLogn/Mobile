//22110367_PhungGiaLong
package com.example.ui_loginregister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIService {
    @PUT("/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);
}


