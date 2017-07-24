package com.ios.downloadlibrary.bitmapState;

import android.graphics.Bitmap;

import com.ios.downloadlibrary.StatusBitmap;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class BitmapStatePreDownload extends BaseBitmapSateProgress{

    public BitmapStatePreDownload(StatusBitmap statusBitmap) {
        super(statusBitmap);
    }

    @Override
    public Bitmap decodeBitmap() {
        return super.decodeBitmap();
    }
}
