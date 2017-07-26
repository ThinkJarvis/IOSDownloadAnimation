package com.gome.drawbitmaplib;

import android.graphics.Bitmap;

import com.gome.drawbitmaplib.bitmapState.BaseBitmapState;


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
