package com.ios.downloadlibrary;

import android.graphics.Bitmap;

import com.ios.downloadlibrary.bitmapState.BaseBitmapState;

/**
 * Created by admin on 2017/7/20.
 */

public class DecodeBitmap {
    BaseBitmapState mBitmapState;
    public DecodeBitmap(BaseBitmapState bitmapState) {
        mBitmapState = bitmapState;
    }

    public Bitmap decode() {
        if (mBitmapState == null) {
            return null;
        }
        return mBitmapState.decodeBitmap();
    }
}
