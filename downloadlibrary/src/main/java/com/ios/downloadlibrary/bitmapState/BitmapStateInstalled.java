package com.ios.downloadlibrary.bitmapState;

import android.graphics.Bitmap;

import com.ios.downloadlibrary.StatusBitmap;

/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BitmapStateInstalled extends BaseBitmapSateProgress{
    public BitmapStateInstalled(StatusBitmap statusBitmap) {
        super(statusBitmap);
    }

    @Override
    public Bitmap decodeBitmap() {
        mCircleSchedule = 0;
        return super.decodeBitmap();
    }
}
