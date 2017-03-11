package com.hust.thanglv.nlpkimdung.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.ListChapter;
import com.hust.thanglv.nlpkimdung.R;
import com.hust.thanglv.nlpkimdung.model.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 3/4/2017.
 */

public class StoryAdapter extends RecyclerView.Adapter {
    private ArrayList<Story> stories;
    private Activity activity;
    private View view;

    public StoryAdapter(Activity activity, ArrayList<Story> stories) {
        this.stories = stories;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new StoryHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof StoryHolder) {
            StoryHolder storyHolder = (StoryHolder) holder;

            storyHolder.name.setText(stories.get(position).getTitle());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private ImageView imgNext;

        public StoryHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameStory);
            imgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == itemView){
                Intent intent = new Intent(activity, ListChapter.class);
                intent.putExtra("idStory", stories.get(getAdapterPosition()).getId());
                intent.putExtra("titleStory", stories.get(getAdapterPosition()).getTitle());
                activity.startActivity(intent);
            }
        }
    }
}
