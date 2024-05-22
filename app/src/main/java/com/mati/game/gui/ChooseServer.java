package com.mati.game.gui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.mati.game.R;
import com.mati.game.gui.util.Utils;
import com.mati.launcher.data.model.Servers;
import com.nvidia.devtech.NvEventQueueActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Formatter;

public class ChooseServer {

    ConstraintLayout serverLayout;
    ArrayList<Servers> mServers;
    TextView percentText;
    Activity aactivity;
    int type, size;
    TextView mProgress;
    LinearLayout linearLayout2;
    public ImageView hud_x2;
    public ImageView hud_gps;
    public ImageView hud_zona;

    public ImageView background;

    RoundCornerProgressBar progressbar;

    public ChooseServer(Activity activity) {
        aactivity = activity;
        hud_gps = aactivity.findViewById(R.id.imageView16);
        hud_zona = aactivity.findViewById(R.id.grzona);
        hud_x2 = aactivity.findViewById(R.id.imageView17);
        serverLayout = activity.findViewById(R.id.br_serverselect_layout);
        percentText = activity.findViewById(R.id.loading_percent);
        type = NvEventQueueActivity.getInstance().getLastServer();
        progressbar = aactivity.findViewById(R.id.progress_bar);
        mServers = new ArrayList<Servers>();
        mServers.add(new Servers("FF0000",
                "(X3)",
                "Persian",
                8,
                1000
        ));

        //Glide.get(aactivity).clearDiskCache();
        //Glide.get(aactivity).clearMemory();

        String bgUrl = "http://62.3.14.22/dl/background.jpg";
        ImageView background = aactivity.findViewById(R.id.image_bg);
        Glide
                .with(aactivity)
                .load(R.drawable.launcher_downloader_bg)
                .error(R.drawable.launcher_downloader_bg)
                .placeholder(R.drawable.background_gradients)
                .load(bgUrl)
                .into(background);

        /*Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://vbd.fdv.dd/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		Interface sInterface = retrofit.create(Interface.class);
		Call<List<Servers>> scall = sInterface.getServers();
		scall.enqueue(new Callback<List<Servers>>() {
			@Override
			public void onResponse(Call<List<Servers>> call, Response<List<Servers>> response) {

				List<Servers> servers = response.body();

				for (Servers server : servers) {
					mServers.add(new Servers(server.getColor(), server.getDopname(), server.getname(), server.getOnline(), server.getmaxOnline()));
				}
			}

			@Override
			public void onFailure(Call<List<Servers>> call, Throwable t) {
				Toast.makeText(activity, "Не удалось подключится к серверу, попробуйте перезайти", Toast.LENGTH_SHORT).show();
			}
		});*/

        Utils.HideLayout(serverLayout, false);
    }

    public void Update(int percent) {
        if (percent <= 100) {
            percentText.setText(new Formatter().format("%d%s", Integer.valueOf(percent), "%").toString());
            progressbar.setProgress(percent);
        } else {
            initUi();
        }
    }

    public void initUi() {
        String str = "fsdf";
        try {
            NvEventQueueActivity.getInstance().sendRPC(2, str.getBytes("windows-1251"), type);
            Utils.HideLayout(serverLayout, true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int size = (mServers.size() / 4) + 1;
        int i4 = 0;
        while (i4 < size) {
            linearLayout2 = new LinearLayout(aactivity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1 / size;
            if (i4 < size - 1) {
                layoutParams.setMargins(0, 0, 0, NvEventQueueActivity.dpToPx(12.0f, aactivity));
            }
            linearLayout2.setLayoutParams(layoutParams);
            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
            int i5 = 0;
            while (i5 < 4) {
                int i6 = (i4 * 4) + i5;
                if (i6 >= mServers.size()) {
                    break;
                }
                final int size2 = (mServers.size() - i6) - 1;
                View inflate = ((LayoutInflater) aactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.br_serverselect_server, null, false);
                inflate.setOnClickListener(view -> {
                    view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                    try {
                        NvEventQueueActivity.getInstance().sendRPC(2, str.getBytes("windows-1251"), size2);
                        Utils.HideLayout(serverLayout, true);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                ((TextView) inflate.findViewById(R.id.br_server_name)).setText(mServers.get(size2).getname());
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(NvEventQueueActivity.dpToPx(160.0f, aactivity), NvEventQueueActivity.dpToPx(80.0f, aactivity));
                layoutParams2.weight = 0.25f;
                if (i4 < 5) {
                    layoutParams2.setMargins(0, 0, NvEventQueueActivity.dpToPx(20.0f, aactivity), 0);
                }
                inflate.setLayoutParams(layoutParams2);
                View findViewById3 = inflate.findViewById(R.id.server_list_back_color);
                RoundCornerProgressBar roundCornerProgressBar = inflate.findViewById(R.id.br_server_progress);
                ImageView imageView = inflate.findViewById(R.id.br_server_image);
                Drawable background = findViewById3.getBackground();
                background.setColorFilter(Color.parseColor("#" + mServers.get(size2).getColor()), PorterDuff.Mode.SRC_ATOP);
                roundCornerProgressBar.setProgressColor(Color.parseColor("#" + mServers.get(size2).getColor()));
                roundCornerProgressBar.setProgress((float) ((int) ((Double.parseDouble(String.valueOf(mServers.get(size2).getOnline())) / Double.parseDouble(String.valueOf(mServers.get(size2).getmaxOnline()))) * 100.0d)));
                imageView.setColorFilter(Color.parseColor("#" + mServers.get(size2).getColor()), PorterDuff.Mode.SRC_ATOP);
                ((TextView) inflate.findViewById(R.id.br_server_online)).setText(Html.fromHtml(mServers.get(size2).getOnline() + "<font color='#808080'>/" + mServers.get(size2).getmaxOnline()));
                linearLayout2.addView(inflate);
                i5++;
            }
            i4++;
        }
    }

    public void Show() {
        Utils.ShowLayout(serverLayout, false);
    }

}