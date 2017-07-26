package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;

import com.gome.drawbitmaplib.BitmapInfo;


/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BitmapStateInstalling extends BaseBitmapSateProgress{
    public BitmapStateInstalling(BitmapInfo bitmapInfo) {
        super(bitmapInfo);

    }

    @Override
    public Bitmap decodeBitmap() {
        mCircleSchedule = -90 + mBitmapInfo.getCircleSchedule() * 18;
        return super.decodeBitmap();
    }
}
