package com.example.nuovo_pt.api;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExerciseRepository {

    private static ExerciseRepository exerciseRepository;
    private WgerAPI wgerAPI;

    private ExerciseRepository(WgerAPI wgerAPI) {
        this.wgerAPI = wgerAPI;
    }

    public static ExerciseRepository getInstance() {
        if (exerciseRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://wger.de/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            exerciseRepository = new ExerciseRepository(retrofit.create(WgerAPI.class));
        }

        return exerciseRepository;
    }

    public void getExercises(final OnGetAPIResponseCallBack callback) {
        wgerAPI.getAPIResponse()
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            List<Result> exercises = response.body().getResults();
                            if (exercises != null) {
                                Log.d("exercise","onresponse is good");
                                callback.onSuccess(exercises);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("exercise","onresponse is bad");
                        callback.onError();
                    }
                });
    }
}
