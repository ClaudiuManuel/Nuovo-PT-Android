package com.example.nuovo_pt.api;

import com.example.nuovo_pt.api.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WgerAPI {
    @GET("api/v2//exerciseinfo/?language=2")
    Call<ApiResponse> getAPIResponse();
}
