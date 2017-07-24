package com.ios.downloadlibrary.bitmapState;

import android.graphics.Bitmap;

import com.ios.downloadlibrary.StatusBitmap;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public abstract class BaseBitmapState {
    protected StatusBitmap mStatusBitmap;
    protected Bitmap mRendererBitmap;

    public BaseBitmapState(StatusBitmap statusBitmap) {
        mRendererBitmap = statusBitmap.createRendererBitmap();
        mStatusBitmap = statusBitmap;
    }

    public Bitmap decodeBitmap() {
        return mRendererBitmap;
    }
}
