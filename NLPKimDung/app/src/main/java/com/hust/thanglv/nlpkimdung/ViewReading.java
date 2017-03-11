package com.hust.thanglv.nlpkimdung;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewReading extends AppCompatActivity {
    private TextView tvReading;
    private String titleChapter, detailReading;
    private CustomActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reading);
        getDataFromIntent();
        init();
    }

    private void getDataFromIntent(){
        titleChapter = getIntent().getStringExtra("titleChapter");
        detailReading = getIntent().getStringExtra("content");
    }
    private void init(){
        actionBar = new CustomActionBar();
        actionBar.eventToolbar(this, titleChapter, true);

        tvReading = (TextView) findViewById(R.id.tvDetailReading);

        tvReading.setText(Html.fromHtml(detailReading));


    }

}
