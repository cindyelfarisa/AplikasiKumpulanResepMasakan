package com.semangatbelajar.aplikasikumpulanresepmasakan;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("/api/recipes/{page}")
    Call<ResponseRecipes> getRecipes(@Path("page") String page);

    @GET("/api/recipe/{key}")
    Call<Results> getDetails(@Path("key") String key);

    @GET("api/search/?")
    Call<ResponseRecipes> getSearch(@Query("q") String search);



//
//    @GET("/api/recipes/")
//    public void getAllRecipes(Callback<ResponseRecipes> callback);
//
//    @GET("/api/search/?")
//    public void getSearch(@Query("q") String search, Callback<List<ModelRecipes>> callback);
}
