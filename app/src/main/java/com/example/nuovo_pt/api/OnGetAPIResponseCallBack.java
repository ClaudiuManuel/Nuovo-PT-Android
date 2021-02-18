package com.example.nuovo_pt.api;

import com.example.nuovo_pt.api.Result;

import java.util.List;

public interface OnGetAPIResponseCallBack {
    void onSuccess(List<Result> exercises);
    void onError();
}
