package com.thanglv.nlp.nlp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thanglv.nlp.nlp.customize.CustomActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewReading extends AppCompatActivity {
    private TextView tvReading;
    private String titleChapter, titleStory, detailReading;
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
        titleStory = getIntent().getStringExtra("titleStory");
    }
    private void init(){
        actionBar = new CustomActionBar();
        actionBar.eventToolbar(this, titleChapter, true);

        tvReading = (TextView) findViewById(R.id.tvDetailReading);
        AssetManager assetManager = getAssets();
        detailReading = readFileAsset(assetManager, "data/" + titleStory + "/" + titleChapter + ".txt");
        tvReading.setText(detailReading);


    }
    public String readFileAsset(AssetManager mgr, String path) {
        String contents = "";
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = mgr.open(path);
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            contents = reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents += '\n' + line;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }

        return contents;
    }
}
