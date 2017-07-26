package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.gome.drawbitmaplib.BitmapInfo;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public abstract class BaseBitmapState {
    protected BitmapInfo mBitmapInfo;
    protected Bitmap mRendererBitmap;
    protected Paint mDrawPaint;

    public BaseBitmapState(BitmapInfo bitmapInfo) {
        mRendererBitmap = bitmapInfo.createRendererBitmap();
        mBitmapInfo = bitmapInfo;

        mDrawPaint = new Paint();
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStyle(Paint.Style.FILL);
    }

    public Bitmap decodeBitmap() {
        return mRendererBitmap;
    }
}
