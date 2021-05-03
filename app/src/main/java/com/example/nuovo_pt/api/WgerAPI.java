package com.example.nuovo_pt.api;

import com.example.nuovo_pt.api.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WgerAPI {
    @GET("api/v2/exerciseinfo/?language=2&limit=150")
    Call<ApiResponse> getAPIResponse();
}
