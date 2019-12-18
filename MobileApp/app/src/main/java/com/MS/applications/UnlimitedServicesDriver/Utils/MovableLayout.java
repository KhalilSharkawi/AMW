package com.MS.applications.UnlimitedServicesDriver.Utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@CoordinatorLayout.DefaultBehavior(MoveUpwardBehavior.class)
public class MovableLayout extends LinearLayout {
    public MovableLayout(Context context) {
        super(context);
    }

    public MovableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

