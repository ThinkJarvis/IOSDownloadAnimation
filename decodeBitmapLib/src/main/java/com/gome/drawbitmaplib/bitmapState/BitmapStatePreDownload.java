package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;

import com.gome.drawbitmaplib.BitmapInfo;


/**
 * Created by weijiaqi on 2017/7/22.
 */

public class BitmapStatePreDownload extends BaseBitmapSateProgress{

    public BitmapStatePreDownload(BitmapInfo bitmapInfo) {
        super(bitmapInfo);
    }

    @Override
    public Bitmap decodeBitmap() {
        return super.decodeBitmap();
    }
}
