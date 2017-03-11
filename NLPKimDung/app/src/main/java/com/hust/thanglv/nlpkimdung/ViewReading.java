package com.hust.thanglv.nlpkimdung;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;
import com.hust.thanglv.nlpkimdung.model.SplitText;
import com.hust.thanglv.nlpkimdung.rules.Rule;
import com.hust.thanglv.nlpkimdung.rules.Rule1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ViewReading extends AppCompatActivity implements View.OnClickListener {
    private TextView tvReading;
    private String titleChapter, detailReadingHtml, detailReading;
    private CustomActionBar actionBar;
    private Button showErr;
    private List<Rule> listRule;
    private String errorWords = "";
    private List<String> lsError = new ArrayList<>();
    private SplitText splitText;
    private List<String> listText = new ArrayList<>();
    private int numOfErr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reading);
        getDataFromIntent();
        init();
    }

    private void getDataFromIntent() {
        titleChapter = getIntent().getStringExtra("titleChapter");
        detailReadingHtml = getIntent().getStringExtra("content");
    }

    private void init() {
        actionBar = new CustomActionBar();
        detailReading = String.valueOf(Html.fromHtml(detailReadingHtml));
        actionBar.eventToolbar(this, titleChapter, true);
        listRule = new ArrayList<>();
        listRule.add(new Rule1());

        tvReading = (TextView) findViewById(R.id.tvDetailReading);
        tvReading.setText(detailReading);

        showErr = (Button) findViewById(R.id.btnShowErr);
        showErr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == showErr) {
            splitText = new SplitText(detailReading.toLowerCase());
            listText = splitText.splitSentences();

            for (int i = 0; i < listText.size(); i++) {
                if (!lsError.toString().contains(listText.get(i))) {
                    for (int k = 0; k < listRule.size(); k++) {
                        if (listRule.get(k).checkInvalidate(listText.get(i))) {
                            numOfErr++;
                            Log.e("thang", String.valueOf(numOfErr));
                            lsError.add(listText.get(i));
                            break;
                        }
                    }
                }
            }
            if(!lsError.isEmpty()) {
                for (int i = 0; i < lsError.size(); i++) {
                    String str = lsError.get(i);
                    tvReading.setText(lsError.get(i));
                }
            }
        }
    }
}
