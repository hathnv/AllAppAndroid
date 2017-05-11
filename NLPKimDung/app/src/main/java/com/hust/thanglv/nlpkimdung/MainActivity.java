package com.hust.thanglv.nlpkimdung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hust.thanglv.nlpkimdung.adapter.StoryAdapter;
import com.hust.thanglv.nlpkimdung.databases.DatabaseHelper;
import com.hust.thanglv.nlpkimdung.model.Story;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listStory;
    private ArrayList<Story> stories;
    private StoryAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        listStory = (RecyclerView) findViewById(R.id.listStory);
        databaseHelper = new DatabaseHelper(this);
        stories = databaseHelper.getListStory();


        adapter = new StoryAdapter(MainActivity.this, stories);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listStory.setLayoutManager(mLinearLayoutManager);
        listStory.setAdapter(adapter);
    }
}
