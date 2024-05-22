package com.mati.launcher.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.mati.game.R;
import com.mati.launcher.adapter.SliderStoriesAdapter;
import com.mati.launcher.utils.Lists;
import com.mati.launcher.view.MainActivity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class StoryActivity extends AppCompatActivity {

    private RoundCornerProgressBar progressStory;
    private SliderStoriesAdapter sliderStoriesAdapter;
    private SliderView sliderView;
    private CountDownTimer countDownTimer;

    //private SwipeRefreshLayout mSwipeRefreshLayout;

    private long progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storie);

        this.sliderView = (SliderView) findViewById(R.id.sliderView);
        this.progressStory = (RoundCornerProgressBar) findViewById(R.id.progressStory);

        final int[] like = {0};

        ImageView likeStory = findViewById(R.id.likeStories);


        TextView numberLike = findViewById(R.id.numberStories);

        likeStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (like[0] == 0) {
                    like[0] = 1;
                    numberLike.setText("1001");
                    likeStory.setImageDrawable(
                            ContextCompat.getDrawable(
                                    getApplicationContext(), R.drawable.like
                            )
                    );
                } else {
                    like[0] = 0;
                    numberLike.setText("1000");
                    likeStory.setImageDrawable(
                            ContextCompat.getDrawable(
                                    getApplicationContext(), R.drawable.un_like
                            )
                    );
                }
            }
        });

        ((ImageView) findViewById(R.id.closeStory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoryActivity.this.lambda$onCreate$0$StoryActivity(view);
            }
        });
        SliderStoriesAdapter sliderStoriesAdapter = new SliderStoriesAdapter(this);
        this.sliderStoriesAdapter = sliderStoriesAdapter;
        this.sliderView.setSliderAdapter(sliderStoriesAdapter);
        this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.sliderStoriesAdapter.addItems(Lists.nlist);
        this.sliderView.setCurrentPageListener(i -> startTimer());
        int intExtra = getIntent().getIntExtra("position", 0);
        this.sliderView.getSliderPager().setCurrentItem(intExtra, false);
        this.sliderView.getPagerIndicator().setSelection(intExtra);
        this.progressStory.setProgress(0.0f);
        startTimer();
    }

    public void lambda$onCreate$0$StoryActivity(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_click));
        new Handler().postDelayed(() -> closeStory(), 200);
    }

    public void closeStory() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            progress = 0;
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            progress = 0;
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(5000, 1) {
            @Override
            public void onTick(long j) {
                progress = 5000 - j;
                progressStory.setProgress(progress);
            }

            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onFinish() {
                if (sliderView.getCurrentPagePosition() + 1 == sliderStoriesAdapter.getCount())
                    closeStory();
                else
                    sliderView.setCurrentPagePosition(sliderView.getCurrentPagePosition() + 1);
            }
        }.start();
    }

    /*@Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
                closeStory();
            }
        }, 1);
    }*/
}