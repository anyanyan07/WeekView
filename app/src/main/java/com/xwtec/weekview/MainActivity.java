package com.xwtec.weekview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.xwtec.weekviewlib.listener.DayClickListener;
import com.xwtec.weekviewlib.view.WeekView;

public class MainActivity extends AppCompatActivity implements DayClickListener {
    private WeekView weekView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weekView = findViewById(R.id.week_view);
        weekView.setFragments(getSupportFragmentManager());
        weekView.setDayClickListener(this);
    }

    @Override
    public void onDayClickListener(String clickDate) {
        Toast.makeText(this, clickDate, Toast.LENGTH_SHORT).show();
    }
}
