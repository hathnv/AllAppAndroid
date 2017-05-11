package com.hust.thanglv.nlpkimdung.Anim;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by thanglv on 10/1/2016.
 * Class này dùng để tạo ra các hình ảnh động sử dụng cho view start và loading
 */
public class Animation {
    private ImageView imAnim;
    private AnimationDrawable animationDrawable = null;

    public Animation(ImageView imAnim) {
        this.imAnim = imAnim;
        createAnim();
    }

    /**
     * Hàm này dùng để chạy liên tiếp ảnh được khai báo trong drawable
     */
    private void createAnim(){
        if (imAnim != null) {
            imAnim.setVisibility(View.VISIBLE);
            animationDrawable = (AnimationDrawable) imAnim.getDrawable();
            animationDrawable.setCallback(imAnim);
            animationDrawable.setVisible(true, true);
        }

        if (!animationDrawable.isRunning()) {
            animationDrawable.start();
        } else {
            animationDrawable.stop();
        }
    }
}
