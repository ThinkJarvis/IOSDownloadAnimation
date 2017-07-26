package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;

import com.gome.drawbitmaplib.BitmapInfo;

/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BitmapStateInstalled extends BaseBitmapSateProgress{
    public BitmapStateInstalled(BitmapInfo bitmapInfo) {
        super(bitmapInfo);
    }

    @Override
    public Bitmap decodeBitmap() {
        mCircleSchedule = 0;
        return super.decodeBitmap();
    }
}
