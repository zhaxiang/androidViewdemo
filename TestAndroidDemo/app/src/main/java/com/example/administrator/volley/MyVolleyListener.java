package com.example.administrator.volley;

/**
 * Created by Administrator on 2015/12/23.
 */
abstract public class MyVolleyListener<T>
{
    private ApiResultCustom apiResultCustom;

    public ApiResultCustom getApiResultCustom() {
        return apiResultCustom;
    }

    public void setApiResultCustom(ApiResultCustom apiResultCustom) {
        this.apiResultCustom = apiResultCustom;
    }

    abstract public void onStart();

    abstract public void onResult(String code , String message, T a);
}
