package com.mati.game.gui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mati.game.R;
import com.mati.game.gui.util.Utils;
import com.mati.game.gui.util.SeekArc;
import java.util.Formatter;

public class Speedometer {
    public Activity activity;
    public TextView mCarHP;
    //public ImageView mStrela;
    //public ImageView mStrela2;
    public ImageView mEngine;
    public TextView mFuel;
    public ConstraintLayout mInputLayout;
    public ImageView mLight;
    public ImageView mLock;
    public ImageView mBG;
    //public ImageView mBG2;
    public TextView mMileage;
    public TextView mSpeed;
    public SeekArc mSpeedLine;

    public Speedometer(Activity activity){
        ConstraintLayout relativeLayout = activity.findViewById(R.id.speedometer);
        mInputLayout = relativeLayout;
        mBG = activity.findViewById(R.id.imageView5);
        mSpeed = activity.findViewById(R.id.speed_text);
       // mStrela = activity.findViewById(R.id.strela_levo);
      //  mStrela2 = activity.findViewById(R.id.strela_pravo);
        mFuel = activity.findViewById(R.id.speed_fuel_text);
        mCarHP = activity.findViewById(R.id.speed_car_hp_text);
        mMileage = activity.findViewById(R.id.textView2);
        mSpeedLine = activity.findViewById(R.id.speed_line);
        mEngine = activity.findViewById(R.id.speed_engine_ico);
        //mBG2 = activity.findViewById(R.id.imageView555);
        mLock = activity.findViewById(R.id.speed_lock_ico);
        /*mBG.setOnClickListener( view -> {
            //mBG.setBackgroundResource(R.drawable.speedo_blink);
            BlinkOn();
        });
        mBG2.setOnClickListener( view -> {
            //mBG.setBackgroundResource(R.drawable.speedo_blink);
            BlinkOff();
        });*/
        Utils.HideLayout(relativeLayout, false);
    }

    public void UpdateSpeedInfo(int speed, int fuel, int hp, int mileage, int engine, int light, int belt, int lock){
        hp= (int) hp/10;
        //Utils.HideLayout(mStrela, false);
        //Utils.HideLayout(mStrela2, false);
        mFuel.setText(new Formatter().format("%d", Integer.valueOf(fuel)).toString());
        mMileage.setText(new Formatter().format("%06d", Integer.valueOf(mileage)).toString());
        mCarHP.setText(new Formatter().format("%d%s", Integer.valueOf(hp), "%").toString());
        mSpeedLine.setProgress(speed);
        mSpeed.setText(String.valueOf(speed));
        if (speed == 0) {
            this.mSpeed.setAlpha(0.4f);
            this.mSpeed.setText("000");
            this.mSpeed.setTextColor(Color.parseColor("#D3D3D3"));
        }
        if (speed != 0) {
            this.mSpeed.setAlpha(1.0f);
            this.mSpeed.setText(String.valueOf(speed));
            this.mSpeed.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(engine == 1)
            mEngine.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
        else
            mEngine.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
        if(lock == 1)
            mLock.setColorFilter(Color.parseColor("#00FF00"), PorterDuff.Mode.SRC_IN);
        else
            mLock.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_IN);
    }

    public void ShowSpeed() {
        Utils.ShowLayout(mInputLayout, false);
    }

    /*public void BlinkOn() {
        Utils.ShowLayout(mBG2, false);
    }

    public void BlinkOff() {
        Utils.HideLayout(mBG2, false);
    }*/

    public void HideSpeed() {
        Utils.HideLayout(mInputLayout, false);
    }
}