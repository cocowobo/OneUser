package com.peipao8.vehiclelock.LYangCode.CustomerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeText extends TextView {
    public MarqueeText(Context context) {
        super ( context );
    }

    public MarqueeText(Context context, AttributeSet attrs) { super ( context, attrs ); }

    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super ( context, attrs, defStyle );
    }

    public boolean isFocused() {
        return true;
    }

    @SuppressLint("MissingSuperCall")
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) { }
}