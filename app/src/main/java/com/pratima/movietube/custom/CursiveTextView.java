package com.pratima.movietube.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CursiveTextView extends AppCompatTextView {
    public CursiveTextView(Context context) {
        super(context);
        initView(context);
    }

    public CursiveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CursiveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "alghifari.ttf");
        setTypeface(typeface);
    }
}
