package com.hust.thanglv.nlpkimdung.customize;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.R;

public class ViewDialogForNotification {

    /**
     * Dialog notification bao gồm thông tin và btn xác nhận
     * @param activity
     * @param prob
     * @param msg
     * @param im
     */
    public void showDialog(Activity activity, String prob, String msg, int im) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_noti);

        TextView textProb = (TextView) dialog.findViewById(R.id.tv_prob_dialog);
        textProb.setText(prob);
        TextView text = (TextView) dialog.findViewById(R.id.tv_noti_dialog);
        text.setText(msg);
        ImageView imDialog = (ImageView) dialog.findViewById(R.id.imDialog);

        imDialog.setImageResource(im);

        imDialog.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.anim_icon_dialog));

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }
}