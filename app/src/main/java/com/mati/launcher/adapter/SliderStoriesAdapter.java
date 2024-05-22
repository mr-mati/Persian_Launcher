package com.mati.launcher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.mati.game.R;
import com.mati.launcher.data.model.News;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SliderStoriesAdapter extends SliderViewAdapter<SliderStoriesAdapter.Holder> {

    private final Context context;
    private List<News.Value> stories = new ArrayList();

    public SliderStoriesAdapter(Context context) {
        this.context = context;
    }

    public void addItems(List<News.Value> list) {
        this.stories = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int i) {
        this.stories.remove(i);
        notifyDataSetChanged();
    }

    public void addItem(News.Value story) {
        this.stories.add(story);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup) {
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_slider_story, (ViewGroup) null));
    }

    public void onBindViewHolder(Holder holder, int i) {
        News.Value story = this.stories.get(i);

        if(story.getUrl() != null && !Objects.equals(story.getUrl(), "null")){
            holder.more.setVisibility(View.VISIBLE);
        } else {
            holder.more.setVisibility(View.GONE);
        }

        holder.title.setText(story.getName());
        Glide.with(this.context).load(story.getImgurl()).into(holder.image);
        /*holder.more.setVisibility(action ? View.GONE : View.VISIBLE);*/
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(holder.hcontext, R.anim.button_click));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.hcontext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(story.getUrl())));
                    }
                }, 200);
            }
        });
    }

    @Override
    public int getCount() {
        return this.stories.size();
    }

    public class Holder extends ViewHolder {
        public ImageView image;
        View itemView;
        public FrameLayout more;
        TextView text;
        public TextView title;
        public Context hcontext;

        public Holder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.text = (TextView) view.findViewById(R.id.text);
            this.image = (ImageView) view.findViewById(R.id.image);
            this.more = (FrameLayout) view.findViewById(R.id.more);
            this.hcontext = context;
            this.itemView = view;
        }
    }
}
