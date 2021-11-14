package com.semangatbelajar.aplikasikumpulanresepmasakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritActivity extends AppCompatActivity {
    Toolbar tbFavorite;
    Context context;
    List<String> fav;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        tbFavorite = findViewById(R.id.toolbar_favorit);
        tbFavorite.setTitle("Resep Favorit");
        setSupportActionBar(tbFavorite);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();

        mApiInterface = Api.getClient().create(ApiInterface.class);
        mRecyclerView = (RecyclerView)  findViewById(R.id.rv_fav);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoritesAdapter(fav);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("courses", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
        fav = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (fav == null) {
            // if the array list is empty
            // creating a new array list.
            fav = new ArrayList<>();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}