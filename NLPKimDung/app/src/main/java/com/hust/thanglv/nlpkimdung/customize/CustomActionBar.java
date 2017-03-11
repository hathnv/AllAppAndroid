package com.hust.thanglv.nlpkimdung.customize;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.R;

/**
 * Created by user on 3/5/17.
 */

public class CustomActionBar implements View.OnClickListener{
    private View imBack, imSearch;
    private TextView tvToolbar;
    private Activity activity;

    public void eventToolbar(Activity activity, String text, boolean hasSearch) {
        this.activity = activity;
        imBack = activity.findViewById(R.id.imBack);
        imSearch = activity.findViewById(R.id.imSearch);
        tvToolbar = (TextView) activity.findViewById(R.id.tvToolbar);

        tvToolbar.setText(text);
        if(hasSearch) {
            imSearch.setVisibility(View.VISIBLE);
        }else {
            imSearch.setVisibility(View.GONE);
        }
        imBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == imBack) {
            activity.onBackPressed();
        }
    }
}
