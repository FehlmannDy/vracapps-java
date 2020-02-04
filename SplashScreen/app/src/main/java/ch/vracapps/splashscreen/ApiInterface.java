package ch.vracapps.splashscreen;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String JSONURL = "https://api.edamam.com";

    @GET("search?q=vege&app_id=900da95e&app_key=40698503668e0bb3897581f4766d77f9")
    Call<String> getVegeQuery();

    @GET("search?app_id=900da95e&app_key=40698503668e0bb3897581f4766d77f9")
    Call<String> getRecipes(@Query("q") String strRecipe);

    @GET("search?q=&app_id=900da95e&app_key=40698503668e0bb3897581f4766d77f9")
    Call<String> getDiet(@Query("health") String strDiet, @Query("to") int to);

    @GET("search?app_id=900da95e&app_key=40698503668e0bb3897581f4766d77f9")
    Call<String> getRecipe(@Query("r") String strUri);

}
