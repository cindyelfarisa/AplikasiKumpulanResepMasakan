package com.semangatbelajar.aplikasikumpulanresepmasakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public DataAdapter dataAdapter;
    public RecyclerView recyclerView;
    public ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();

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

        View toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((androidx.appcompat.widget.Toolbar) toolbar);

        //membuat data yang akan ditampilkan dalam list
        //file .html mengambil di folder assets
        inputData("Ayam Bakar Bumbu Bali", "artikel_1.html");
        inputData("Sate Ayam Srepeh", "artikel_2.html");
        inputData("Pizza Sosis Jumbo (Tanpa Ulen)", "artikel_3.html");
        inputData("Nasgor Mawut (Mawut Sayur)", "artikel_4.html");
        inputData("Fuyung Hai", "artikel_5.html");
        inputData("Lobster Bumbu Padang", "artikel_6.html");
        inputData("Sop Iga Sapi", "artikel_7.html");
        inputData("Opor Ayam Kampung", "artikel_8.html");
        inputData("Bebek Goreng Sambel Ijo", "artikel_9.html");
        inputData("Soto Ayam Kampung", "artikel_10.html");
        inputData("Bakso Ayam", "artikel_11.html");
        inputData("Ikan Gurame Bakar", "artikel_12.html");
        inputData("Pisang Bakar Coklat Keju", "artikel_13.html");
        inputData("Keto Martabak Terang Bulan", "artikel_14.html");
        inputData("Ingkung Ayam Kampung", "artikel_15.html");

        //menampilkan data ke dalam recyclerView
        recyclerView = findViewById(R.id.recyclerContentView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dataAdapter = new DataAdapter(this, dataModelArrayList);
        recyclerView.setAdapter(dataAdapter);

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
}