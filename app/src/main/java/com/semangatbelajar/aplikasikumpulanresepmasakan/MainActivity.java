package com.semangatbelajar.aplikasikumpulanresepmasakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Integer page = 1;
    private TextView btnPrev;
    private TextView btnNext;
    private List<ModelRecipes> list;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_status:
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiInterface = Api.getClient().create(ApiInterface.class);
        mRecyclerView = (RecyclerView)  findViewById(R.id.recyclerContentView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btnPrev = findViewById(R.id.btn_pev);
        btnNext = findViewById(R.id.btn_next);

        View toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((androidx.appcompat.widget.Toolbar) toolbar);
        //list.clear();
        refresh(page.toString());
        if (page == 1){
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page += 1;
                    refresh(page.toString() );
                }
            });
            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page -= 1;
                    refresh(page.toString());
                }
            });
        } else {
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page += 1;
                    refresh(page.toString());
                }
            });
            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page -= 1;
                    refresh(page.toString());
                }
            });
        }



//        //membuat data yang akan ditampilkan dalam list
//        //file .html mengambil di folder assets
//        inputData("Ayam Bakar Bumbu Bali", "artikel_1.html");
//        inputData("Sate Ayam Srepeh", "artikel_2.html");
//        inputData("Pizza Sosis Jumbo (Tanpa Ulen)", "artikel_3.html");
//        inputData("Nasgor Mawut (Mawut Sayur)", "artikel_4.html");
//        inputData("Fuyung Hai", "artikel_5.html");
//        inputData("Lobster Bumbu Padang", "artikel_6.html");
//        inputData("Sop Iga Sapi", "artikel_7.html");
//        inputData("Opor Ayam Kampung", "artikel_8.html");
//        inputData("Bebek Goreng Sambel Ijo", "artikel_9.html");
//        inputData("Soto Ayam Kampung", "artikel_10.html");
//        inputData("Bakso Ayam", "artikel_11.html");
//        inputData("Ikan Gurame Bakar", "artikel_12.html");
//        inputData("Pisang Bakar Coklat Keju", "artikel_13.html");
//        inputData("Keto Martabak Terang Bulan", "artikel_14.html");
//        inputData("Ingkung Ayam Kampung", "artikel_15.html");
//
//        //menampilkan data ke dalam recyclerView
//        recyclerView = findViewById(R.id.recyclerContentView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        dataAdapter = new DataAdapter(this, dataModelArrayList);
//        recyclerView.setAdapter(dataAdapter);

        /*//menambahakan header
        DataModel headerModel = new DataModel();
        headerModel.setViewType(2);
        dataModelArrayList.add(0, headerModel);*/

        //menambahkan footer
        DataModel footerModel = new DataModel();
        footerModel.setViewType(2);
        dataModelArrayList.add(footerModel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //Create Notification
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this, NotifReceiver.class);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);

        createNotificationChannel();

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) getSystemService
                (ALARM_SERVICE);

        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
        createNotificationChannel();
    }

    //fungsi input
    public void inputData(String judul, String konten) {
        DataModel dataModel = new DataModel();
        dataModel.setJudul(judul);
        dataModel.setKonten(konten);
        dataModel.setViewType(1);
        dataModelArrayList.add(dataModel);
    }


    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void Info(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        startActivity(intent);
    }

    public void Favorite(MenuItem item){
        Intent intent = new Intent(MainActivity.this, FavoritActivity.class);
        startActivity(intent);
    }

    public void Search(MenuItem item){
        list.clear();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Call<ResponseRecipes> recipes = mApiInterface.getSearch(s);
                recipes.enqueue(new Callback<ResponseRecipes>() {
                    @Override
                    public void onResponse(Call<ResponseRecipes> call, Response<ResponseRecipes> response) {
                        list = response.body().getRecipes();
                        mAdapter = new RecipesAdapter(list);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        btnNext.setVisibility(View.GONE);
                        btnPrev.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ResponseRecipes> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }

    void refresh(String page){
        Call<ResponseRecipes> recipes = mApiInterface.getRecipes(page);
        recipes.enqueue(new Callback<ResponseRecipes>() {
            @Override
            public void onResponse(Call<ResponseRecipes> call, Response<ResponseRecipes> response) {
                list = response.body().getRecipes();
                mAdapter = new RecipesAdapter(list);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseRecipes> call, Throwable t) {

            }
        });
    }

    public void createNotificationChannel() {
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Notification",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 1 day to " +
                    "Open your app !");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}