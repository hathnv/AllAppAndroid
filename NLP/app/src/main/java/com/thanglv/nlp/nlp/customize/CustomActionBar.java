package com.thanglv.nlp.nlp.customize;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thanglv.nlp.nlp.R;

/**
 * Created by user on 3/5/17.
 */

public class CustomActionBar implements View.OnClickListener{
    private View imBack;
    private TextView tvToolbar;
    private Activity activity;

    public void eventToolbar(Activity activity, String text) {
        this.activity = activity;
        imBack = activity.findViewById(R.id.imBack);
        tvToolbar = (TextView) activity.findViewById(R.id.tvToolbar);

        tvToolbar.setText(text);
        imBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == imBack) {
            activity.onBackPressed();
        }
    }
}
