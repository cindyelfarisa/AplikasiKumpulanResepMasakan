package com.semangatbelajar.aplikasikumpulanresepmasakan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    WebView webView;
    private ProgressBar mProgressBar;
    private ApiInterface mApiInterface;
    private ImageView imgRecipe;
    private TextView txtTitle, txtDesc, txtBahan, txtLangkah;
    private FloatingActionButton btnFav;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mApiInterface = Api.getClient().create(ApiInterface.class);
        setupToolbar();
        this.context = context;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");

        Log.d("inijalan", title);
        //setting judul bar
        String title_fix = title.replace("-", " ").replace("[","").replace("]","");
        String words[] = title_fix.split("\\s");
        String capitalizeStr = "";
        for (String word : words){
            String firstLetter = word.substring(0,1);
            String remaining = word.substring(1);
            capitalizeStr += firstLetter.toUpperCase() + remaining + " ";
        }
        setTitle(capitalizeStr);
        Log.d("inijalan2", capitalizeStr);


        //menampilkan data konten

        Call<Results> recipes = mApiInterface.getDetails(title);
        recipes.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                imgRecipe = findViewById(R.id.img_tumb);
                Picasso.get()
                        .load(response.body().getResult().getThumb())
                        .fit()
                        .into(imgRecipe);

                txtTitle = findViewById(R.id.txt_title);
                txtTitle.setText(response.body().getResult().getTitle());
                txtBahan = findViewById(R.id.txt_bahan);
                String bahan = response.body().getResult().getBahan().toString()
                        .replace("[","")
                        .replace("]","")
                        .replace(",","\n");
                txtBahan.setText(bahan);
                txtLangkah = findViewById(R.id.txt_langkah);
                String step = response.body().getResult().getStep().toString()
                        .replace("[","")
                        .replace("]","")
                        .replace(",","\n");
                txtLangkah.setText(step);

            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });


        btnFav = (FloatingActionButton) findViewById(R.id.fabFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Add to Favorite", Toast.LENGTH_LONG).show();
                Log.d("tesssss", title);
                ArrayList<String> fav = new ArrayList<String>();
                fav.add(title);
                saveData(fav);
            }
        });

    }

    private void saveData(ArrayList<String> fav) {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(fav);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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