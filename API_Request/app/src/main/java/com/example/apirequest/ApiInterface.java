package com.example.apirequest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String JSONURL = "https://api.edamam.com";

    @GET("search?q=vege&app_id=$900da95e&app_key=$40698503668e0bb3897581f4766d77f9")
    Call<String> getString();

}
