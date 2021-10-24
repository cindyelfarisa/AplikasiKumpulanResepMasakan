package com.semangatbelajar.aplikasikumpulanresepmasakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {
    Toolbar tbInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        tbInfo = findViewById(R.id.toolbar_info);
        tbInfo.setTitle("Info");
        setSupportActionBar(tbInfo);
        assert getSupportActionBar() != null;
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