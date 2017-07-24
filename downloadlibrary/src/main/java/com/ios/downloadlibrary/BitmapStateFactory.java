package com.ios.downloadlibrary;

import com.ios.downloadlibrary.bitmapState.BaseBitmapState;
import com.ios.downloadlibrary.bitmapState.BitmapStateDownloaded;
import com.ios.downloadlibrary.bitmapState.BitmapStateDownloading;
import com.ios.downloadlibrary.bitmapState.BitmapStateInstalled;
import com.ios.downloadlibrary.bitmapState.BitmapStateInstalling;
import com.ios.downloadlibrary.bitmapState.BitmapStatePreDownload;
import com.ios.downloadlibrary.bitmapState.BitmapStatePreInstall;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class BitmapStateFactory {
    public static BitmapStateFactory sBitmapStateFactory;

    public static BitmapStateFactory getInstance() {
        if (null == sBitmapStateFactory) {
            sBitmapStateFactory = new BitmapStateFactory();
        }
        return sBitmapStateFactory;
    }

     public BaseBitmapState createBitmapState(StatusBitmap statusBitmap) {
         if (statusBitmap == null) {
             return null;
         }

         BaseBitmapState bitmapState = null;
         switch (statusBitmap.getStatus()) {
             case StatusBitmap.PRE_DOWNLOAD:
                 bitmapState = new BitmapStatePreDownload(statusBitmap);
                 break;

             case StatusBitmap.DOWNLOADING:
                 bitmapState = new BitmapStateDownloading(statusBitmap);
                 break;

             case StatusBitmap.DOWNLOADED:
                 bitmapState = new BitmapStateDownloaded(statusBitmap);
                 break;


             case StatusBitmap.PRE_INSTALL:
                 bitmapState = new BitmapStatePreInstall(statusBitmap);
                 break;

             case StatusBitmap.INSTALLING:
                 bitmapState = new BitmapStateInstalling(statusBitmap);
                 break;

             case StatusBitmap.INSTALLED:
                 bitmapState = new BitmapStateInstalled(statusBitmap);
                 break;

             default:
                 break;
         }

         return bitmapState;
     }
}
