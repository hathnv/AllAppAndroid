package com.thanglv.nlp.nlp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thanglv.nlp.nlp.adapter.ChapterAdapter;
import com.thanglv.nlp.nlp.customize.CustomActionBar;
import com.thanglv.nlp.nlp.model.Chapter;

import java.io.IOException;
import java.util.ArrayList;

public class ListChapter extends AppCompatActivity {
    private RecyclerView listChapter;
    private ArrayList<Chapter> chapters;
    private String [] listTitleChapter;
    private ChapterAdapter adapter;
    private CustomActionBar actionBar;
    private String titleStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        getDataFromIntent();
        init();
    }

    private void getDataFromIntent(){
        titleStory = getIntent().getStringExtra("titleStory");
    }
    private void init(){
        actionBar = new CustomActionBar();
        actionBar.eventToolbar(this, titleStory);
        listChapter = (RecyclerView) findViewById(R.id.list_chapter);
        chapters = new ArrayList<>();

        AssetManager assetManager = getAssets();
        try {
            listTitleChapter  = assetManager.list("data/" + titleStory);

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listTitleChapter.length; i++) {
            String s = ".txt";
            if (listTitleChapter[i].contains(s)) {
                listTitleChapter[i] = listTitleChapter[i].substring(0, (listTitleChapter[i].length() - 4));
            }
            Chapter chuong = new Chapter(listTitleChapter[i]);
            chapters.add(chuong);
        }


        for (int i = 0; i < listTitleChapter.length; i ++) {
            Chapter chapter = new Chapter(listTitleChapter[i]);
            chapters.add(chapter);
            Log.d("chapter", " " + chapters.get(i).getTitle());
        }

        adapter = new ChapterAdapter(ListChapter.this, chapters, titleStory);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listChapter.setLayoutManager(mLinearLayoutManager);
        listChapter.setAdapter(adapter);
    }
}
