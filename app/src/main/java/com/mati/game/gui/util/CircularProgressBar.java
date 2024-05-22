package com.mati.game.gui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mati.game.R;
public class CircularProgressBar extends View {

    /* renamed from: a  reason: collision with root package name */
    public Paint f4115a;

    /* renamed from: b  reason: collision with root package name */
    public float f4116b;
    public int c;

    /* renamed from: d  reason: collision with root package name */
    public RectF f4117d;

    /* renamed from: e  reason: collision with root package name */
    public String f4118e;

    /* renamed from: f  reason: collision with root package name */
    public int f4119f;

    /* renamed from: g  reason: collision with root package name */
    public int f4120g;

    /* renamed from: h  reason: collision with root package name */
    public int f4121h;

    /* renamed from: i  reason: collision with root package name */
    public int f4122i;

    /* renamed from: j  reason: collision with root package name */
    public int f4123j;

    /* renamed from: k  reason: collision with root package name */
    public double f4124k;

    /* renamed from: l  reason: collision with root package name */
    public int f4125l;

    /* renamed from: m  reason: collision with root package name */
    public int f4126m;
    public int n;

    /* renamed from: o  reason: collision with root package name */
    public int f4127o;

    /* renamed from: p  reason: collision with root package name */
    public int f4128p;

    /* renamed from: ru.edgar.matrp.gui.keyboard.q  reason: collision with root package name */
    public int f4129q;

    /* renamed from: r  reason: collision with root package name */
    public int f4130r;

    /* renamed from: s  reason: collision with root package name */
    public int f4131s;

    /* renamed from: u  reason: collision with root package name */
    public boolean f4132u;

    /* renamed from: v  reason: collision with root package name */
    public boolean f4133v;

    public static final int[] K = {R.attr.gaugeDividerColor, R.attr.gaugeDividerDrawFirst, R.attr.gaugeDividerDrawLast, R.attr.gaugeDividerSize, R.attr.gaugeDividerStep, R.attr.gaugeEndValue, R.attr.gaugePointEndColor, R.attr.gaugePointSize, R.attr.gaugePointStartColor, R.attr.gaugeStartAngle, R.attr.gaugeStartValue, R.attr.gaugeStrokeCap, R.attr.gaugeStrokeColor, R.attr.gaugeStrokeWidth, R.attr.gaugeSweepAngle, R.attr.gaugeValue};

    @SuppressLint("ResourceType")
    public CircularProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint.Cap cap = null;
        Paint paint = null;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, K, 0, 0);
        setStrokeWidth(obtainStyledAttributes.getDimension(13, 10.0f));
        setStrokeColor(obtainStyledAttributes.getColor(12, context.getResources().getColor(17170432)));
        setStrokeCap(obtainStyledAttributes.getString(11));
        setStartAngle(obtainStyledAttributes.getInt(9, 0));
        setSweepAngle(obtainStyledAttributes.getInt(14, 360));
        setStartValue(obtainStyledAttributes.getInt(10, 0));
        setEndValue(obtainStyledAttributes.getInt(5, 1000));
        setValue(obtainStyledAttributes.getInt(15, 0));
        setPointSize(obtainStyledAttributes.getInt(7, 0));
        setPointStartColor(obtainStyledAttributes.getColor(8, context.getResources().getColor(17170443)));
        setPointEndColor(obtainStyledAttributes.getColor(6, context.getResources().getColor(17170443)));
        int i10 = obtainStyledAttributes.getInt(3, 0);
        setDividerColor(obtainStyledAttributes.getColor(0, context.getResources().getColor(17170443)));
        int i11 = obtainStyledAttributes.getInt(4, 0);
        setDividerDrawFirst(obtainStyledAttributes.getBoolean(1, true));
        setDividerDrawLast(obtainStyledAttributes.getBoolean(2, true));
        int i12 = this.f4122i;
        int i13 = this.f4121h;
        this.f4124k = ((double) Math.abs(this.f4120g)) / ((double) (i12 - i13));
        if (i10 > 0) {
            this.f4129q = this.f4120g / (Math.abs(i12 - i13) / i10);
            int i14 = 100 / i11;
            this.f4131s = i14;
            this.f4130r = this.f4120g / i14;
        }
        obtainStyledAttributes.recycle();
        Paint paint2 = new Paint();
        this.f4115a = paint2;
        paint2.setColor(this.c);
        this.f4115a.setStrokeWidth(this.f4116b);
        this.f4115a.setAntiAlias(true);
        if (TextUtils.isEmpty(this.f4118e) || this.f4118e.equals("BUTT")) {
            paint = this.f4115a;
            cap = Paint.Cap.BUTT;
        } else {
            if (this.f4118e.equals("ROUND")) {
                paint = this.f4115a;
                cap = Paint.Cap.ROUND;
            }
            this.f4115a.setStyle(Paint.Style.STROKE);
            this.f4117d = new RectF();
            this.f4123j = this.f4121h;
            this.f4125l = this.f4119f;
        }
        paint.setStrokeCap(cap);
        this.f4115a.setStyle(Paint.Style.STROKE);
        this.f4117d = new RectF();
        this.f4123j = this.f4121h;
        this.f4125l = this.f4119f;
    }

    public int getDividerColor() {
        return this.f4128p;
    }

    public int getEndValue() {
        return this.f4122i;
    }

    public int getPointEndColor() {
        return this.f4127o;
    }

    public int getPointSize() {
        return this.f4126m;
    }

    public int getPointStartColor() {
        return this.n;
    }

    public int getStartAngle() {
        return this.f4119f;
    }

    public int getStartValue() {
        return this.f4121h;
    }

    public String getStrokeCap() {
        return this.f4118e;
    }

    public int getStrokeColor() {
        return this.c;
    }

    public float getStrokeWidth() {
        return this.f4116b;
    }

    public int getSweepAngle() {
        return this.f4120g;
    }

    public int getValue() {
        return this.f4123j;
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float strokeWidth = getStrokeWidth();
        float width = ((float) (getWidth() < getHeight() ? getWidth() : getHeight())) - strokeWidth;
        int i10 = (width > width ? 1 : (width == width ? 0 : -1));
        float f10 = width / 2.0f;
        float f11 = strokeWidth * 2.0f;
        this.f4117d.set((((((float) getWidth()) - f11) / 2.0f) - f10) + strokeWidth, (((((float) getHeight()) - f11) / 2.0f) - f10) + strokeWidth, (((((float) getWidth()) - f11) / 2.0f) - f10) + strokeWidth + width, (((((float) getHeight()) - f11) / 2.0f) - f10) + strokeWidth + width);
        this.f4115a.setColor(this.c);
        this.f4115a.setStrokeWidth(this.f4116b);
        this.f4115a.setShader((Shader) null);
        canvas.drawArc(this.f4117d, (float) this.f4119f, (float) this.f4120g, false, this.f4115a);
        this.f4115a.setColor(this.n);
        this.f4115a.setShader(new LinearGradient((float) getWidth(), (float) getHeight(), 0.0f, 0.0f, this.f4127o, this.n, Shader.TileMode.CLAMP));
        int i11 = this.f4126m;
        if (i11 > 0) {
            int i12 = this.f4125l;
            if (i12 > (i11 / 2) + this.f4119f) {
                canvas.drawArc(this.f4117d, (float) (i12 - (i11 / 2)), (float) i11, false, this.f4115a);
            } else {
                canvas.drawArc(this.f4117d, (float) i12, (float) i11, false, this.f4115a);
            }
        } else if (this.f4123j == this.f4121h) {
            canvas.drawArc(this.f4117d, (float) this.f4119f, 1.0f, false, this.f4115a);
        } else {
            RectF rectF = this.f4117d;
            int i13 = this.f4119f;
            canvas.drawArc(rectF, (float) i13, (float) (this.f4125l - i13), false, this.f4115a);
        }
        if (this.f4129q > 0) {
            this.f4115a.setColor(this.f4128p);
            this.f4115a.setShader((Shader) null);
            int i14 = this.f4133v ? this.f4131s + 1 : this.f4131s;
            for (int i15 = 0; i15 < i14; i15++) {
                canvas.drawArc(this.f4117d, (float) ((this.f4130r * i15) + this.f4119f), (float) this.f4129q, false, this.f4115a);
            }
        }
    }

    public void setDividerColor(int i10) {
        this.f4128p = i10;
    }

    public void setDividerDrawFirst(boolean z6) {
        this.f4132u = z6;
    }

    public void setDividerDrawLast(boolean z6) {
        this.f4133v = z6;
    }

    public void setDividerSize(int i10) {
        if (i10 > 0) {
            this.f4129q = this.f4120g / (Math.abs(this.f4122i - this.f4121h) / i10);
        }
    }

    public void setDividerStep(int i10) {
        if (i10 > 0) {
            int i11 = 100 / i10;
            this.f4131s = i11;
            this.f4130r = this.f4120g / i11;
        }
    }

    public void setEndValue(int i10) {
        this.f4122i = i10;
        this.f4124k = ((double) Math.abs(this.f4120g)) / ((double) (this.f4122i - this.f4121h));
        invalidate();
    }

    public void setPointEndColor(int i10) {
        this.f4127o = i10;
    }

    public void setPointSize(int i10) {
        this.f4126m = i10;
    }

    public void setPointStartColor(int i10) {
        this.n = i10;
    }

    public void setStartAngle(int i10) {
        this.f4119f = i10;
    }

    public void setStartValue(int i10) {
        this.f4121h = i10;
    }

    public void setStrokeCap(String str) {
        Paint paint;
        Paint.Cap cap;
        this.f4118e = str;
        if (this.f4115a != null) {
            if (str.equals("BUTT")) {
                paint = this.f4115a;
                cap = Paint.Cap.BUTT;
            } else if (this.f4118e.equals("ROUND")) {
                paint = this.f4115a;
                cap = Paint.Cap.ROUND;
            } else {
                return;
            }
            paint.setStrokeCap(cap);
        }
    }

    public void setStrokeColor(int i10) {
        this.c = i10;
    }

    public void setStrokeWidth(float f10) {
        this.f4116b = f10;
    }

    public void setSweepAngle(int i10) {
        this.f4120g = i10;
    }

    public void setValue(int i10) {
        this.f4123j = i10;
        this.f4125l = (int) ((((double) (i10 - this.f4121h)) * this.f4124k) + ((double) this.f4119f));
        invalidate();
    }
}
