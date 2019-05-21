package com.ios.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2018/3/9.
 */

public class Utils {

    private static final Canvas sCanvas = new Canvas();
    private static final Rect sOldBounds = new Rect();
    static int sColors[] = {0xffff0000, 0xff00ff00, 0xff0000ff};
    static int sColorIndex = 0;

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public static Bitmap createIconBitmap(Context context, Drawable srcDrawable, Drawable inDrawable) {
        if (null == srcDrawable) {
            return null;
        }
        Bitmap srcBitmap = drawableToBitmap(srcDrawable);
        Bitmap inBitmap = drawableToBitmap(inDrawable);
        if (null == inBitmap) {
            return srcBitmap;
        }
        Drawable toolDrawable = new BitmapDrawable(context.getResources(), inBitmap.copy(Bitmap.Config.ARGB_8888, true));
        Drawable sDrawable = new BitmapDrawable(context.getResources(), srcBitmap);
        inBitmap.recycle();

        Bitmap toolIcon = createIconBitmap(toolDrawable, context, (int) context.getResources().getDimension(R.dimen.icon_size));
        Bitmap fixBitmap = createIconBitmap(sDrawable, context, (int) context.getResources().getDimension(R.dimen.icon_size));
        return combineOrClipBitmap(toolIcon, fixBitmap, PorterDuff.Mode.SRC_IN);
    }

    public static Bitmap combineOrClipBitmap(Bitmap toolIcon, Bitmap srcBitmap,
                                             PorterDuff.Mode mode) {
        Paint bitPaint = new Paint();
        bitPaint.setAntiAlias(true);
        Canvas canvas = new Canvas(toolIcon);
        canvas.drawBitmap(toolIcon, 0, 0, bitPaint);
        float translateX = (toolIcon.getWidth() - srcBitmap.getWidth()) / 2.0f;
        float translateY = (toolIcon.getHeight() - srcBitmap.getHeight()) / 2.0f;
        bitPaint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(srcBitmap, translateX, translateY, bitPaint);
        bitPaint.setXfermode(null);
        canvas.setBitmap(null);
        if (!srcBitmap.isRecycled()) {
            srcBitmap.recycle();
            srcBitmap = null;
        }
        return toolIcon;
    }

    public static Bitmap createIconBitmap(Drawable icon, Context context, int iconSize) {
        synchronized (sCanvas) {
            final int iconBitmapSize = iconSize;

            int width = iconBitmapSize;
            int height = iconBitmapSize;

            if (icon instanceof BitmapDrawable) {
                // Ensure the bitmap has a density.
                BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                    bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
                }
            }
            int sourceWidth = icon.getIntrinsicWidth();
            int sourceHeight = icon.getIntrinsicHeight();
            if (sourceWidth > 0 && sourceHeight > 0) {
                // Scale the bg_app proportionally to the bg_app dimensions
                final float ratio = (float) sourceWidth / sourceHeight;
                if (sourceWidth > sourceHeight) {
                    height = (int) (width / ratio);
                } else if (sourceHeight > sourceWidth) {
                    width = (int) (height * ratio);
                }
            }

            // no intrinsic size --> use default size
            int textureWidth = iconBitmapSize;
            int textureHeight = iconBitmapSize;

            final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = sCanvas;
            canvas.setBitmap(bitmap);

            final int left = (textureWidth - width) / 2;
            final int top = (textureHeight - height) / 2;

            sOldBounds.set(icon.getBounds());
            icon.setBounds(left, top, left + width, top + height);
            canvas.save();
            icon.draw(canvas);
            canvas.restore();
            icon.setBounds(sOldBounds);
            canvas.setBitmap(null);

            return bitmap;
        }
    }
}
