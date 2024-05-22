package com.mati.game.gui;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mati.game.R;
import com.mati.game.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;

public class HudManager {
    public Activity activity;

    public ConstraintLayout hud_layout;

    private final NvEventQueueActivity mContext = null;

    public TextView hud_money;

    public TextView txt_playerid;
    public TextView txt_hp;
    public TextView txt_armour;

    public TextView txt_ammo;

    public TextView txt_hunger;

    public ImageView hud_weapon;

    public ImageView hud_radar;

    public ImageView hud_x2;

    public ImageView hud_tab;

    public ImageView hud_menu;

    public ImageView hud_micro;

    public ImageView hud_shagerd;

    public ImageView hud_gps;

    public ImageView hud_zona;

    public ArrayList<ImageView> hud_wanted;

    //public ProgressBar progressHP;
    //public ProgressBar progressArmor;

    public HudManager(Activity aactivity) {
        activity = aactivity;
        hud_layout = aactivity.findViewById(R.id.hud_main);
        hud_layout.setVisibility(View.GONE);

        //progressArmor = aactivity.findViewById(R.id.hud_armor_pb);
        //progressHP = aactivity.findViewById(R.id.hud_health_pb);

        hud_radar = aactivity.findViewById(R.id.radar_hude);

        /*hud_micro = aactivity.findViewById(R.id.img_voice);*/

        hud_shagerd = aactivity.findViewById(R.id.img_shagerd);

        hud_gps = aactivity.findViewById(R.id.imageView16);
        hud_zona = aactivity.findViewById(R.id.grzona);
        hud_x2 = aactivity.findViewById(R.id.imageView17);

        txt_playerid = aactivity.findViewById(R.id.txt_playerid);

        txt_hp = aactivity.findViewById(R.id.txt_hp);
        txt_armour = aactivity.findViewById(R.id.txt_armour);
        txt_ammo = aactivity.findViewById(R.id.txt_ammo);
        txt_hunger = aactivity.findViewById(R.id.txt_hunger);

        hud_money = aactivity.findViewById(R.id.hud_money);
        hud_weapon = aactivity.findViewById(R.id.hud_weapon);
        hud_tab = aactivity.findViewById(R.id.hud_tab);
        hud_menu = aactivity.findViewById(R.id.hud_menu);

        hud_wanted = new ArrayList<>();
        hud_wanted.add(activity.findViewById(R.id.hud_star_1));
        hud_wanted.add(activity.findViewById(R.id.hud_star_2));
        hud_wanted.add(activity.findViewById(R.id.hud_star_3));
        hud_wanted.add(activity.findViewById(R.id.hud_star_4));
        hud_wanted.add(activity.findViewById(R.id.hud_star_5));
        hud_wanted.add(activity.findViewById(R.id.hud_star_6));

        hud_shagerd.setOnClickListener(view -> {
            NvEventQueueActivity.getInstance().enterSandliShagerd();
        });

        hud_menu.setOnClickListener(view -> {
            //view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().showMenuu();
            //NvEventQueueActivity.getInstance().togglePlayer(1);
        });

        hud_tab.setOnClickListener(view -> {
            NvEventQueueActivity.getInstance().showTab();
        });

    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponidweik, int ammo, int playerid, int money, int wanted) {
        //progressHP.setProgress(health);
        //progressArmor.setProgress(armour);

        DecimalFormat formatter = new DecimalFormat();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        txt_playerid.setText(formatter.format(playerid));
        hud_money.setText(formatter.format(money));
        txt_hp.setText(formatter.format(health));
        txt_hunger.setText(formatter.format(hunger));
        txt_armour.setText(formatter.format(armour));
        txt_ammo.setText(formatter.format(ammo));

        int id = activity.getResources().getIdentifier(new Formatter().format("weapon_%d", Integer.valueOf(weaponidweik)).toString(), "drawable", activity.getPackageName());
        hud_weapon.setImageResource(id);

        hud_weapon.setOnClickListener(v -> NvEventQueueActivity.getInstance().onWeaponChanged());
        if (wanted > 6) wanted = 6;
        for (int i2 = 0; i2 < wanted; i2++) {
            hud_wanted.get(i2).setBackgroundResource(R.drawable.ic_y_star);
        }

        //Toast.makeText(aactivity, "X:" + (int)hud_radar.getX() + " Y:" + (int)hud_radar.getY(), Toast.LENGTH_LONG).show();
        //System by Weikton, ALL REVERS: F*UCK YOU
        // NvEventQueueActivity.setNativeHudElementPosition(6, (int)hud_radar.getX(), (int)hud_radar.getY());
        // NvEventQueueActivity.setNativeHudElementScale(6, (int)hud_radar.getX(), (int)hud_radar.getY())/*, (int)hud_radar.getScaleType()*/;
    }

    public void ShowGps() {
        Utils.ShowLayout(hud_gps, false);
    }

    public void HideGps() {
        Utils.HideLayout(hud_gps, false);
    }

    public void ShowX2() {
        Utils.ShowLayout(hud_x2, false);
    }

    public void HideX2() {
        Utils.HideLayout(hud_x2, false);
    }

    public void ShowZona() {
        Utils.ShowLayout(hud_zona, false);
    }

    public void HideZona() {
        Utils.HideLayout(hud_zona, false);
    }

    public void ShowRadar() {
        Utils.ShowLayout(hud_radar, true);
    }

    public void HideRadar() {
        Utils.HideLayout(hud_radar, false);
    }

    public void ShowHud() {
        Utils.ShowLayout(hud_layout, true);
        Utils.ShowLayout(hud_micro, false);
        //Utils.ShowLayout(hud_shagerd, true);
    }

    public void ShowShagerd() {
        Utils.ShowLayout(hud_shagerd, true);
    }

    public void HideShagerd() {
        Utils.HideLayout(hud_shagerd, false);
    }

    public void HideHud() {
        Utils.HideLayout(hud_layout, false);
        Utils.HideLayout(hud_micro, false);
        Utils.HideLayout(hud_shagerd, false);
    }

}
