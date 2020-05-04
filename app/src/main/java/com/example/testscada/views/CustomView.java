package com.example.testscada.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private final static int SCALE_WIDTH = 40;
    private final static int SCALE_HEIGHT = 720;
    private int height;

    //Gray Rectangle
    private Rect mRectGray;
    private Paint mRectPaintGray;

    //Gray Blue
    private Rect mRectBlue;
    private Paint mRectPaintBlue;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(@Nullable AttributeSet set){
        mRectGray = new Rect();
        mRectPaintGray = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRectBlue = new Rect();
        mRectPaintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRectGray.left = 0;
        mRectGray.top = 0;
        mRectGray.right = SCALE_WIDTH;
        mRectGray.bottom = SCALE_HEIGHT;

        mRectBlue.left = 2;
        mRectBlue.top = SCALE_HEIGHT - 2;
        mRectBlue.right = SCALE_WIDTH - 2;
        mRectBlue.bottom = SCALE_HEIGHT - 2;

        mRectPaintGray.setColor(Color.rgb(204,204,204));
        mRectPaintBlue.setColor(Color.rgb(115,194,251)); // Maya blue
    }

    public void UpdateSize(int h){
        this.height = ((SCALE_HEIGHT - 2) * h) / 100;

        mRectBlue.top = SCALE_HEIGHT - 2 - this.height;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawRect(mRectGray, mRectPaintGray);
        canvas.drawRect(mRectBlue, mRectPaintBlue);
    }
}
