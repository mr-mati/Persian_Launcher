package com.mati.launcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mati.game.R;
import com.mati.launcher.activity.StoryActivity;
import com.mati.launcher.data.model.News;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context context;

    ArrayList<News.Value> nlist;

    public NewsAdapter(Context context, ArrayList<News.Value> nlist) {
        this.context = context;
        this.nlist = nlist;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News.Value news = nlist.get(position);
        holder.title.setText(news.getName());
        Glide
                .with(context)
                .load(news.getImgurl())
                .placeholder(R.drawable.background_gradients)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(holder.image);
        Glide
                .with(context)
                .load(news.getImgurl())
                .circleCrop()
                .into(holder.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                Intent intent = new Intent(context, StoryActivity.class);
                intent.putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nlist.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        ImageView profile;

        public NewsViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvNewsText);
            image = itemView.findViewById(R.id.ivNewsImage);
            profile = itemView.findViewById(R.id.iwNewsImage);
        }
    }


}