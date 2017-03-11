package com.thanglv.nlp.nlp;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.thanglv.nlp.nlp.adapter.StoryAdapter;
import com.thanglv.nlp.nlp.customize.CustomActionBar;
import com.thanglv.nlp.nlp.model.Story;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView listStory;
    private ArrayList<Story> stories;
    private String [] listTitleStory;
    private StoryAdapter adapter;
    private CustomActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        actionBar = new CustomActionBar();
        actionBar.eventToolbar(this, "Truyện Kim Dung", false);
        listStory = (RecyclerView) findViewById(R.id.listStory);
        stories = new ArrayList<>();

        AssetManager assetManager = getAssets();
        try {
            listTitleStory  = assetManager.list("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listTitleStory.length; i ++) {
            Story truyen = new Story(listTitleStory[i]);
            stories.add(truyen);
            Log.d("title", " " + stories.get(i).getTitle());
        }
        adapter = new StoryAdapter(MainActivity.this, stories);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listStory.setLayoutManager(mLinearLayoutManager);
        listStory.setAdapter(adapter);
    }
}
