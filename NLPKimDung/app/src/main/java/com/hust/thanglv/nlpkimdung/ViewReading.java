package com.hust.thanglv.nlpkimdung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.customize.CustomActionBar;
import com.hust.thanglv.nlpkimdung.customize.ViewDialogForNotification;
import com.hust.thanglv.nlpkimdung.model.SplitText;
import com.hust.thanglv.nlpkimdung.rules.CacPhuAmNamCuoi;
import com.hust.thanglv.nlpkimdung.rules.CheckC;
import com.hust.thanglv.nlpkimdung.rules.CheckH;
import com.hust.thanglv.nlpkimdung.rules.CheckK;
import com.hust.thanglv.nlpkimdung.rules.CheckP;
import com.hust.thanglv.nlpkimdung.rules.CheckPhuAmGanNhau;
import com.hust.thanglv.nlpkimdung.rules.CheckQ;
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

public class ViewReading extends AppCompatActivity implements View.OnClickListener {
    private TextView tvReading;
    private String titleChapter, detailReadingHtml, detailReading;
    private CustomActionBar actionBar;
    private Button showErr, btnOK, btnDetail;
    private List<Rule> listRule;
    private String errorWords = "";
    private List<String> lsError;
    private SplitText splitText;
    private List<String> listText;
    private int numOfErr = 0;
    private ViewDialogForNotification notification;
    private TextView tvNameStory, tvDetailErr;
    private ScrollView scrollView;
    private final DisplayMetrics dm = new DisplayMetrics();
    private View viewDetailErr;
    String err = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reading);
        getDataFromIntent();
        init();
    }

    /**
     * Lấy dữ liệu từ intent
     */
    private void getDataFromIntent() {
        titleChapter = getIntent().getStringExtra("titleChapter");
        detailReadingHtml = getIntent().getStringExtra("content");
    }

    private void init() {
        actionBar = new CustomActionBar();
        detailReading = String.valueOf(Html.fromHtml(detailReadingHtml));
        notification = new ViewDialogForNotification();
        tvNameStory = (TextView) findViewById(R.id.tvNameStory);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        viewDetailErr = findViewById(R.id.view_detail_err);
        viewDetailErr.setVisibility(View.GONE);
        tvDetailErr = (TextView) findViewById(R.id.tv_detail_err);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnDetail = (Button) findViewById(R.id.btnDetailErr);
        btnDetail.setVisibility(View.GONE);

        actionBar.eventToolbar(this, titleChapter, false);
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

        tvReading = (TextView) findViewById(R.id.tvDetailReading);
        tvReading.setText(detailReading);
        Log.d("scroll", String.valueOf(scrollView.getChildAt(0).getHeight()));
        Log.d("heightText", String.valueOf(getTextHeight(tvReading)) + " and " + String.valueOf(getScreenHeight())
                + " num " + String.valueOf(numPage(getTextHeight(tvReading), getScreenHeight() - 480)));

        showErr = (Button) findViewById(R.id.btnShowErr);
        showErr.setText("Error");
        showErr.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
    }

    /**
     * Lấy độ cao của textview
     *
     * @param text
     * @return
     */
    private int getTextHeight(TextView text) {
        text.measure(0, 0);
        return text.getMeasuredHeight();
    }

    /**
     * Lấy độ cao của màn hình thiết bị
     *
     * @return
     */
    private int getScreenHeight() {
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * Lấy chiều rộng của thiết bị
     *
     * @return
     */
    private int getScreenWidth() {
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * Số trang của văn bản
     *
     * @param textHeight   chiều cao textview
     * @param screenHeight chiều cao màn hình
     * @return mỗi màn hình là 1 trang, dựa vào height xác định số trang của văn bản
     */
    private int numPage(int textHeight, int screenHeight) {
        int num = 0;
        num = textHeight / screenHeight;
        if (textHeight > screenHeight * num) {
            num += 1;
        }
        return num;
    }

    @Override
    public void onClick(View v) {
        if (v == showErr) {
            err = "";
            lsError = new ArrayList<>();
            listText = new ArrayList<>();
            numOfErr = 0;
            btnDetail.setVisibility(View.VISIBLE);
            viewDetailErr.setVisibility(View.GONE);

            splitText = new SplitText(detailReading.toLowerCase());
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

            notification.showDialog(ViewReading.this, "Thông báo",
                    "Số lỗi của chương này là " + String.valueOf(numOfErr), R.drawable.check_button);
        }
        if (v == btnDetail) {
            showErr.setVisibility(View.GONE);
            viewDetailErr.setVisibility(View.VISIBLE);
            tvDetailErr.setText(err);
        }
        if (v == btnOK) {
            viewDetailErr.setVisibility(View.GONE);
            btnDetail.setVisibility(View.GONE);
            showErr.setVisibility(View.VISIBLE);
        }
    }
}
