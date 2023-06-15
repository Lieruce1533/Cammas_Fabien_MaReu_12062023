package com.artus.mareu.ui.meetings_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mareu.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        AndroidThreeTen.init(this);
    }
}