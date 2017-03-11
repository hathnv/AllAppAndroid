package com.hust.thanglv.nlpkimdung;

import android.content.res.AssetManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hust.thanglv.nlpkimdung.adapter.ChapterAdapter;
import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;
import com.hust.thanglv.nlpkimdung.databases.DatabaseHelper;
import com.hust.thanglv.nlpkimdung.model.Chapter;

import java.io.IOException;
import java.util.ArrayList;

public class ListChapter extends AppCompatActivity {
    private RecyclerView listChapter;
    private ArrayList<Chapter> chapters;
    private ChapterAdapter adapter;
    private CustomActionBar actionBar;
    private DatabaseHelper databaseHelper;
    private String titleStory;
    private int idStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        getDataFromIntent();
        init();
    }

    private void getDataFromIntent(){
        titleStory = getIntent().getStringExtra("titleStory");
        idStory = getIntent().getIntExtra("idStory", -1);
    }

    private void init(){
        actionBar = new CustomActionBar();
        actionBar.eventToolbar(this, titleStory, false);
        listChapter = (RecyclerView) findViewById(R.id.list_chapter);
        databaseHelper = new DatabaseHelper(this);
        chapters = databaseHelper.getListChapter(idStory);

        adapter = new ChapterAdapter(ListChapter.this, chapters);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listChapter.setLayoutManager(mLinearLayoutManager);
        listChapter.setAdapter(adapter);
    }
}
