package com.hust.thanglv.nlpkimdung.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hust.thanglv.nlpkimdung.R;
import com.hust.thanglv.nlpkimdung.ViewReading;
import com.hust.thanglv.nlpkimdung.model.Chapter;
import com.hust.thanglv.nlpkimdung.model.ResultSearchModel;

import java.util.ArrayList;

/**
 * Created by user on 3/5/17.
 */

public class ResultSearchAdapter extends RecyclerView.Adapter {
    private ArrayList<ResultSearchModel> resultSearchModels;
    private Activity activity;
    private int pos;
    private View view;

    public ResultSearchAdapter(Activity activity, ArrayList<ResultSearchModel> resultSearchModels) {
        this.resultSearchModels = resultSearchModels;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_search, parent, false);
        return new SearchHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchHolder) {
            SearchHolder searchHolder = (SearchHolder) holder;

            searchHolder.tvChapter.setText(resultSearchModels.get(position).getNumChapter());
            searchHolder.tvContent.setText(resultSearchModels.get(position).getContent());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return resultSearchModels.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvChapter, tvContent;
        private ImageView imgNext;

        public SearchHolder(View itemView) {
            super(itemView);
            tvChapter = (TextView) itemView.findViewById(R.id.tvChapterSearch);
            tvContent = (TextView) itemView.findViewById(R.id.tvContentSearch);
            imgNext = (ImageView) itemView.findViewById(R.id.imgNext);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == itemView) {
                Intent intent = new Intent(activity, ViewReading.class);
                intent.putExtra("offset", resultSearchModels.get(getAdapterPosition())
                        .getPosSearchText());
                intent.putExtra("titleChapter", resultSearchModels.get(getAdapterPosition())
                        .getChapter().getTitle());
                intent.putExtra("content", resultSearchModels.get(getAdapterPosition())
                        .getChapter().getContent());
                intent.putExtra("search", resultSearchModels.get(getAdapterPosition())
                        .getTextSearch());
                activity.startActivity(intent);
            }
        }
    }
}
