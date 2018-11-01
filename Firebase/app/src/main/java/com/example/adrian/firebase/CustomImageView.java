package com.example.adrian.firebase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by Adrian on 03/04/2018.
 */

//Creating a circle Image View for circular Image.
//https://stackoverflow.com/questions/6199008/optimizations-of-custom-imageview-that-frequently-refreshes-by-calling-ondraw?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    public static float radius = 20.0f;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}