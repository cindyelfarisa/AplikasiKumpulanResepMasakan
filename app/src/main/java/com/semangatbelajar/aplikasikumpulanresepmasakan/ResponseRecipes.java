package com.semangatbelajar.aplikasikumpulanresepmasakan;

import android.view.Display;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseRecipes {

    @SerializedName("method")
    private String method;

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private List<ModelRecipes> recipes;

    public String getStatus() {
        return status;
    }

    public String getMethod() {
        return method;
    }

    public List<ModelRecipes> getRecipes() {
        return recipes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRecipes(List<ModelRecipes> recipes) {
        this.recipes = recipes;
    }
}
