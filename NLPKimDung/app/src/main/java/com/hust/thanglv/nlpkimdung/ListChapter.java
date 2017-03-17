package com.hust.thanglv.nlpkimdung;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hust.thanglv.nlpkimdung.adapter.ChapterAdapter;
import com.hust.thanglv.nlpkimdung.adapter.ResultSearchAdapter;
import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;
import com.hust.thanglv.nlpkimdung.databases.DatabaseHelper;
import com.hust.thanglv.nlpkimdung.model.Chapter;
import com.hust.thanglv.nlpkimdung.model.ResultSearchModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ListChapter extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView listChapter, listResultSearch;
    private ArrayList<Chapter> chapters;
    private ChapterAdapter adapter;
    private ResultSearchAdapter searchAdapter;
    private CustomActionBar actionBar;
    private DatabaseHelper databaseHelper;
    private String titleStory;
    private int idStory;
    private ImageView imSearch, btnClose;
    private Button btnCancel, btnSearch;
    private TextView tvStory;
    private EditText edPassage;
    private View view_dialog_search, view_result_search;
    private ArrayList<ResultSearchModel> resultSearchs;
    private ResultSearchModel resultSearch;
    private LinearLayout layoutSearchVoice;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        getDataFromIntent();
        init();
    }

    /**
     * Lấy dữ liệu thông qua intent
     */
    private void getDataFromIntent(){
        titleStory = getIntent().getStringExtra("titleStory");
        idStory = getIntent().getIntExtra("idStory", -1);
    }

    /**
     * Khai báo các view và khởi tạo giá trị
     */
    private void init(){
        actionBar = new CustomActionBar();

        actionBar.eventToolbar(this, titleStory, true);
        listChapter = (RecyclerView) findViewById(R.id.list_chapter);
        listResultSearch = (RecyclerView) findViewById(R.id.listResultSearch);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvStory = (TextView) findViewById(R.id.tvStory);
        edPassage = (EditText) findViewById(R.id.edPassage);
        view_dialog_search = findViewById(R.id.view_dialog_search);
        view_result_search = findViewById(R.id.view_result_search);
        view_dialog_search.setVisibility(View.GONE);
        view_result_search.setVisibility(View.GONE);
        layoutSearchVoice = (LinearLayout) findViewById(R.id.layout_search_voice);

        databaseHelper = new DatabaseHelper(this);
        chapters = databaseHelper.getListChapter(idStory);
        imSearch = (ImageView) findViewById(R.id.imSearch);
        btnClose = (ImageView) findViewById(R.id.btnClose);
        tvStory.setText(titleStory);

        adapter = new ChapterAdapter(ListChapter.this, chapters);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listChapter.setLayoutManager(mLinearLayoutManager);
        listChapter.setAdapter(adapter);

        imSearch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        layoutSearchVoice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == imSearch){
            view_dialog_search.setVisibility(View.VISIBLE);
        }
        if(v == btnCancel ){
            view_dialog_search.setVisibility(View.GONE);
        }
        if(v == btnClose){
            view_result_search.setVisibility(View.GONE);
        }
        // xử lý sự kiện khi click vào btn search
        // hiển thị view để tìm kiếm
        if(v == btnSearch){
            String textSearch = edPassage.getText().toString().toLowerCase();
            resultSearchs = new ArrayList<>();

            for(int i = 0; i < chapters.size(); i++){
                String content = String.valueOf(Html.fromHtml(chapters.get(i).getContent().toLowerCase()));
                if(chapters.get(i).getContent().toLowerCase().contains(textSearch)){
                    resultSearch = new ResultSearchModel("Chương " + String.valueOf(i + 1), content.substring(content
                            .indexOf(textSearch), content
                            .indexOf(textSearch) + + textSearch.length() + 50));
                    resultSearchs.add(resultSearch);
                    Log.d("findtext", resultSearch.getChapter() + resultSearch.getContent());
                }
            }
            Log.d("result", String.valueOf(resultSearchs.size()));

            if (resultSearchs.size() == 0){
                Toast.makeText(getApplicationContext(), "can't find text", Toast.LENGTH_LONG).show();
                view_dialog_search.setVisibility(View.GONE);
            }else {
                view_result_search.setVisibility(View.VISIBLE);
                searchAdapter = new ResultSearchAdapter(ListChapter.this, resultSearchs);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

                listResultSearch.setLayoutManager(mLinearLayoutManager);
                listResultSearch.setAdapter(searchAdapter);

            }
        }

        if (v == layoutSearchVoice){
            /*
            search voice Chức năng tìm kiếm bằng giọng nói
             */
            promptSpeechInput();
        }
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edPassage.setText(result.get(0));
                }
                break;
            }
        }
    }
}
