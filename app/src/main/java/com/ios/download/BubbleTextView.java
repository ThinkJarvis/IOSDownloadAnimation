package com.ios.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ios.downloadlibrary.BitmapStateFactory;
import com.ios.downloadlibrary.DecodeBitmap;
import com.ios.downloadlibrary.StatusBitmap;
import com.ios.downloadlibrary.bitmapState.BaseBitmapState;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class BubbleTextView extends TextView{
    private StatusBitmap mStatusBitmap;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        mStatusBitmap = new StatusBitmap(this);
        initMaskBitmap(mStatusBitmap);
    }

    private void initMaskBitmap(StatusBitmap statusBitmap) {
        Bitmap maskBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_mask);
        statusBitmap.setMaskBitmap(maskBitmap);
    }

    private void initView(Context context) {
        setDrawableTop(R.drawable.icon);
        this.setText("app");
    }


    public StatusBitmap getStatusBitmap() {
        return mStatusBitmap;
    }


    public void setStatusBitmap(StatusBitmap statusBitmap) {
        mStatusBitmap = statusBitmap;
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
        drawableTop.setBounds(0,0,bitmap.getWidth(),bitmap.getHeight());
        this.setCompoundDrawables(null,drawableTop,null,null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mStatusBitmap.getStatus() == StatusBitmap.ICON_READY) {
            setDrawableTop(mStatusBitmap.getIconBitmap());
        }else {
            BaseBitmapState bitmapState = BitmapStateFactory.getInstance().createBitmapState(mStatusBitmap);
            DecodeBitmap DecodeBitmap = new DecodeBitmap(bitmapState);
            if(null != DecodeBitmap.decode()) {
                canvas.drawBitmap(DecodeBitmap.decode(), 0, 0, mStatusBitmap.getDefaultPaint());
            }
        }


    }
}
