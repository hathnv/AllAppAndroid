package com.thanglv.nlp.nlp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.thanglv.nlp.nlp.R;
import com.thanglv.nlp.nlp.ViewReading;
import com.thanglv.nlp.nlp.model.Chapter;

import java.util.ArrayList;

/**
 * Created by user on 3/5/17.
 */

public class ChapterAdapter  extends RecyclerView.Adapter {
    private ArrayList<Chapter> chapters;
    private Activity activity;
    private View view;
    String titleStory;

    public ChapterAdapter(Activity activity, ArrayList<Chapter> chapters, String titleStory) {
        this.chapters = chapters;
        this.activity = activity;
        this.titleStory = titleStory;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ChapterHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChapterHolder) {
            ChapterHolder chapterHolder = (ChapterHolder) holder;

            chapterHolder.name.setText(chapters.get(position).getTitle());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    class ChapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private ImageView imgNext;

        public ChapterHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameStory);
            imgNext = (ImageView) itemView.findViewById(R.id.imgNext);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == itemView) {
                Intent intent = new Intent(activity, ViewReading.class);
                intent.putExtra("titleChapter", chapters.get(getAdapterPosition()).getTitle());
                intent.putExtra("titleStory", titleStory);
                activity.startActivity(intent);
            }
        }
    }
}
