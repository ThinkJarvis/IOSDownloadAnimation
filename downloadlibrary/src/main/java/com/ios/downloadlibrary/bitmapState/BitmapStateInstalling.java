package com.ios.downloadlibrary.bitmapState;

import android.graphics.Bitmap;

import com.ios.downloadlibrary.StatusBitmap;

/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BitmapStateInstalling extends BaseBitmapSateProgress{
    public BitmapStateInstalling(StatusBitmap statusBitmap) {
        super(statusBitmap);

    }

    @Override
    public Bitmap decodeBitmap() {
        mCircleSchedule = -90 + mStatusBitmap.getCircleSchedule() * 18;
        return super.decodeBitmap();
    }
}
