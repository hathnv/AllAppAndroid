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

import com.hust.thanglv.nlpkimdung.Anim.Animation;
import com.hust.thanglv.nlpkimdung.adapter.ChapterAdapter;
import com.hust.thanglv.nlpkimdung.adapter.ResultSearchAdapter;
import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;
import com.hust.thanglv.nlpkimdung.customize.ViewDialogForNotification;
import com.hust.thanglv.nlpkimdung.databases.DatabaseHelper;
import com.hust.thanglv.nlpkimdung.model.Chapter;
import com.hust.thanglv.nlpkimdung.model.ResultSearchModel;
import com.hust.thanglv.nlpkimdung.model.SplitText;
import com.hust.thanglv.nlpkimdung.rules.CacPhuAmNamCuoi;
import com.hust.thanglv.nlpkimdung.rules.CheckC;
import com.hust.thanglv.nlpkimdung.rules.CheckH;
import com.hust.thanglv.nlpkimdung.rules.CheckK;
import com.hust.thanglv.nlpkimdung.rules.CheckP;
import com.hust.thanglv.nlpkimdung.rules.CheckPhuAmGanNhau;
import com.hust.thanglv.nlpkimdung.rules.CheckQ;
import com.hust.thanglv.nlpkimdung.rules.CheckS;
import com.hust.thanglv.nlpkimdung.rules.CheckT;
import com.hust.thanglv.nlpkimdung.rules.CheckTr;
import com.hust.thanglv.nlpkimdung.rules.Rule;
import com.hust.thanglv.nlpkimdung.rules.Rule1;
import com.hust.thanglv.nlpkimdung.rules.Rule2;
import com.hust.thanglv.nlpkimdung.rules.Rule3;
import com.hust.thanglv.nlpkimdung.rules.Rule4;
import com.hust.thanglv.nlpkimdung.rules.Rule5;
import com.hust.thanglv.nlpkimdung.rules.Rule6;
import com.hust.thanglv.nlpkimdung.rules.Rule7;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListChapter extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView listChapter, listResultSearch;
    private ArrayList<Chapter> chapters;
    private ChapterAdapter adapter;
    private ResultSearchAdapter searchAdapter;
    private CustomActionBar actionBar;
    private DatabaseHelper databaseHelper;
    private String titleStory;
    private int idStory;
    private ImageView imSearch, btnClose;
    private Button btnCancel, btnSearch, btnShowAllErr, btnDetailAllErr, btnOK;
    private TextView tvStory, tvDetailErr;
    private EditText edPassage;
    private View view_dialog_search, view_result_search;
    private ArrayList<ResultSearchModel> resultSearchs;
    private ResultSearchModel resultSearch;
    private LinearLayout layoutSearchVoice;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private List<Rule> listRule;
    private List<String> lsError;
    private SplitText splitText;
    private List<String> listText;
    private int numOfErr = 0;
    private ViewDialogForNotification notification;
    String err = "";
    private View viewDetailErr;

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
    private void getDataFromIntent() {
        titleStory = getIntent().getStringExtra("titleStory");
        idStory = getIntent().getIntExtra("idStory", -1);
    }

    /**
     * Khai báo các view và khởi tạo giá trị
     */
    private void init() {
        actionBar = new CustomActionBar();
        listRule = new ArrayList<>();
        listRule.add(new Rule1());
        listRule.add(new Rule2());
        listRule.add(new Rule3());
        listRule.add(new Rule4());
        listRule.add(new Rule5());
        listRule.add(new Rule6());
        listRule.add(new Rule7());
        listRule.add(new CheckC());
        listRule.add(new CheckH());
        listRule.add(new CacPhuAmNamCuoi());
        listRule.add(new CheckK());
        listRule.add(new CheckP());
        listRule.add(new CheckPhuAmGanNhau());
        listRule.add(new CheckQ());
        listRule.add(new CheckT());
        listRule.add(new CheckTr());
        listRule.add(new CheckS());

        actionBar.eventToolbar(this, titleStory, true);
        listChapter = (RecyclerView) findViewById(R.id.list_chapter);
        listResultSearch = (RecyclerView) findViewById(R.id.listResultSearch);
        notification = new ViewDialogForNotification();
        viewDetailErr = findViewById(R.id.view_detail_err);
        viewDetailErr.setVisibility(View.GONE);
        tvDetailErr = (TextView) findViewById(R.id.tv_detail_err);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnShowAllErr = (Button) findViewById(R.id.btnShowAllErr);
        btnDetailAllErr = (Button) findViewById(R.id.btnDetailAllErr);
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
        btnOK = (Button) findViewById(R.id.btnOK);
        tvStory.setText(titleStory);

        adapter = new ChapterAdapter(ListChapter.this, chapters);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        listChapter.setLayoutManager(mLinearLayoutManager);
        listChapter.setAdapter(adapter);

        imSearch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnDetailAllErr.setOnClickListener(this);
        btnShowAllErr.setOnClickListener(this);
        layoutSearchVoice.setOnClickListener(this);
        btnOK.setOnClickListener(this);
    }

    /**
     * Hàm tìm kiếm trong cả bộ truyện
     * @param textSearch
     */
    private void searchText(String textSearch){
        resultSearchs = new ArrayList<>();

        for(int i = 0; i < chapters.size(); i++){
            ArrayList<Integer> positions = new ArrayList();
            String content = String.valueOf(Html.fromHtml(chapters.get(i).getContent().toLowerCase()));

            if(content.contains(textSearch)){

                int index = content.indexOf(textSearch);

                // Duyệt tất cả các vị trí xuất hiện textSearch
                while (index >= 0) {
                    positions.add(index);
                    index = content.indexOf(textSearch, index + 1);
                }

                for (int p : positions) {
                    Log.d("position", String.valueOf(p) + " " );
                    String s = "";
                    if(content.length() - p - textSearch.length() < 50) {
                        s = content.substring(p, content.length());
                    }else {
                        if(p > 10) {
                            s = content.substring(p - 10, p + textSearch.length() + 50);
                        }else {
                            s = content.substring(p, p + textSearch.length() + 50);
                        }
                    }
                    Log.d("timchu", String.valueOf(p) + " " + s);
                    resultSearch = new ResultSearchModel(chapters.get(i), s, p, "Chương " + String.valueOf(i + 1), textSearch);
                    resultSearchs.add(resultSearch);
                    Log.d("findtext", resultSearch.getChapter() + resultSearch.getContent());
                }
            }

        }
        Log.d("result", String.valueOf(resultSearchs.size()));

        if (resultSearchs.size() == 0) {
            Toast.makeText(getApplicationContext(), "can't find text", Toast.LENGTH_LONG).show();
            view_dialog_search.setVisibility(View.GONE);
        } else {
            view_result_search.setVisibility(View.VISIBLE);
            searchAdapter = new ResultSearchAdapter(ListChapter.this, resultSearchs);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

            listResultSearch.setLayoutManager(mLinearLayoutManager);
            listResultSearch.setAdapter(searchAdapter);

        }
    }

    @Override
    public void onClick(View v) {
        if (v == imSearch) {
            view_dialog_search.setVisibility(View.VISIBLE);
        }
        if (v == btnCancel) {
            view_dialog_search.setVisibility(View.GONE);
        }
        if (v == btnClose) {
            view_result_search.setVisibility(View.GONE);
        }
        if(v == btnShowAllErr){
            String content = "";
            for(int i = 0; i < chapters.size(); i++) {
                content += String.valueOf(Html.fromHtml(chapters.get(i).getContent().toLowerCase()));
            }
            err = "";
            lsError = new ArrayList<>();
            listText = new ArrayList<>();
            numOfErr = 0;
            btnDetailAllErr.setVisibility(View.VISIBLE);
            viewDetailErr.setVisibility(View.GONE);

            splitText = new SplitText(content.toLowerCase());
            listText = splitText.splitSentences();

            for (int i = 0; i < listText.size(); i++) {
                for (int k = 0; k < listRule.size(); k++) {
                    if (listRule.get(k).checkInvalidate(listText.get(i))) {
                        numOfErr++;
                        Log.d("thang", "Từ này sai ở luật thứ " + (k + 1)
                                + " " + listText.get(i) + " " + listRule.get(k).getClass().getSimpleName());
                        err += listText.get(i) + ", ";
                        lsError.add(listText.get(i));
                        break;
                    }
                }
            }
            notification.showDialog(ListChapter.this, "Thông báo",
                    "Số lỗi của truyện này là " + String.valueOf(numOfErr), R.drawable.check_button);
        }
        if (v == btnDetailAllErr){
            btnShowAllErr.setVisibility(View.GONE);
            viewDetailErr.setVisibility(View.VISIBLE);
            tvDetailErr.setText(err);
        }
        if(v == btnOK){
            viewDetailErr.setVisibility(View.GONE);
        }
        // xử lý sự kiện khi click vào btn search
        // hiển thị view để tìm kiếm
        if (v == btnSearch) {

            String textSearch = edPassage.getText().toString().toLowerCase();
            if(textSearch.length() == 0){
                Toast.makeText(getApplicationContext(), "Nhập vào từ cần tìm", Toast.LENGTH_LONG).show();
            }else {
                searchText(textSearch);
            }

        }

        if (v == layoutSearchVoice) {
            /*
            search voice Chức năng tìm kiếm bằng giọng nói
             */
            promptSpeechInput();
        }
    }

    /**
     * Showing google speech input dialog
     */
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
