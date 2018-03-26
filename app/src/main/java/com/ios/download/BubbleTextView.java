package com.ios.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gome.drawbitmaplib.BitmapInfo;
import com.gome.drawbitmaplib.BitmapStateFactory;
import com.gome.drawbitmaplib.DecodeBitmap;
import com.gome.drawbitmaplib.bitmapState.BaseBitmapState;


/**
 * Created by weijiaqi on 2017/7/22.
 */

public class BubbleTextView extends TextView{
    private BitmapInfo mBitmapInfo;
    private Bitmap mMaskBitmap;
    private int mIconSize;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIconSize = (int) context.getResources().getDimension(R.dimen.icon_size);
        Drawable maskDrawable = getResources().getDrawable(R.drawable.app_mask);
        Drawable bgDrawable = getResources().getDrawable(R.drawable.bg_app);
        mMaskBitmap= Utils.createIconBitmap(context,maskDrawable,bgDrawable);
        initView();
        initBitmapInfo();
    }



    private void initView() {
        setDrawableTop(R.drawable.pictures);
        this.setText("app");
    }

    private void initBitmapInfo() {
        mBitmapInfo = new BitmapInfo();
        mBitmapInfo.setStatus(BitmapInfo.NONE);
    }


    public BitmapInfo getBitmapInfo() {
        return mBitmapInfo;
    }


    public void setBitmapInfo(BitmapInfo bitmapInfo) {
        mBitmapInfo = bitmapInfo;
        postInvalidate();
    }


    public void setDrawableTop(int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawableId);
        setCompoundDrawables(bitmap);
    }

    public void setDrawableTop(Bitmap bitmap) {
        setCompoundDrawables(bitmap);
    }


    private void setCompoundDrawables(Bitmap bitmap) {
        Drawable drawableTop = new BitmapDrawable(bitmap);
        drawableTop.setBounds(0,0,mIconSize,mIconSize);
        this.setCompoundDrawables(null,drawableTop,null,null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmapInfo.getStatus() == BitmapInfo.NONE) {
            Rect rect = new Rect(getScrollX() + 0 + getPaddingLeft(),
                    getScrollY() + 0 + getPaddingTop(),
                    getScrollX() + getWidth() - getPaddingRight(),
                    getScrollY() + getCompoundPaddingTop());

            Rect rendererRect = new Rect(rect.centerX() - mIconSize / 2,
                    rect.centerY() - mIconSize / 2,
                    rect.centerX() + mIconSize / 2,
                    rect.centerY() +mIconSize / 2);

            mBitmapInfo.setRendererRect(rendererRect);
            mBitmapInfo.setMaskBitmap(mMaskBitmap);
        }else {
            BaseBitmapState bitmapState = BitmapStateFactory.getInstance().createBitmapState(mBitmapInfo);
            DecodeBitmap DecodeBitmap = new DecodeBitmap(bitmapState);
            if(null != DecodeBitmap.decode()) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawBitmap(DecodeBitmap.decode(), getScrollX(), getScrollY(), paint);
            }
        }


    }
}
