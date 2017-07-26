package com.gome.drawbitmaplib;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.math.BigDecimal;

/**
 * Created by admin on 2017/7/24.
 */

public class BitmapInfo {
    public static final int NONE = 0x01;
    public static final int ICON_READY = NONE << 1;
    public static final int PRE_DOWNLOAD = NONE << 2;
    public static final int DOWNLOADING = NONE << 3;
    public static final int DOWNLOADED = NONE << 4;
    public static final int PRE_INSTALL = NONE << 5;
    public static final int INSTALLING = NONE << 6;
    public static final int INSTALLED = NONE << 7;
    public static final int NORMAL = NONE << 8;
    public static final int DOWNLOAD_ERROR = NONE << 9;
    public static final int INSTALL_ERROR = NONE << 10;

    private Bitmap mMaskBitmap;

    private int mStatus;
    private Point mCenterPoint;
    private Rect mRendererRect;
    private float mCircleRadius;
    private float mCircleSchedule;
    private float mMaskInterpolator;
    private float mCircleInterpolator;

    public BitmapInfo() {
    }

    private void initConfig() {
        mCenterPoint = new Point(mRendererRect.width() / 2,mRendererRect.height() / 2);
        mCircleRadius =  Math.min(mRendererRect.width(), mRendererRect.height()) / 3;
    }

    public Bitmap createRendererBitmap() {
        Bitmap rendererBitmap = null;
        if (mRendererRect != null) {
            rendererBitmap = Bitmap.createBitmap(mRendererRect.width(),
                    mRendererRect.height(), Bitmap.Config.ARGB_8888);
        }
        return rendererBitmap;
    }

    private float divide(double a,double b) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        return (float) b1.divide(b2, 10, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    private void updateMaskBitmap() {
        Matrix matrix = new Matrix();

        float scaleX = divide(mRendererRect.width(),mMaskBitmap.getWidth());
        float scaleY = divide(mRendererRect.height(),mMaskBitmap.getHeight());

        matrix.postScale(scaleX,scaleY);
        mMaskBitmap = Bitmap.createBitmap(mMaskBitmap,0,0,mMaskBitmap.getWidth(),mMaskBitmap.getHeight(),matrix,true);
    }

    public Rect getRendererRect() {
        return mRendererRect;
    }


    public void setRendererRect(Rect rendererRect) {
        this.mRendererRect = rendererRect;
        initConfig();
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public Point getCenterPoint() {
        return mCenterPoint;
    }

    public void setCenterPoint(Point centerPoint) {
        this.mCenterPoint = centerPoint;
    }

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.mCircleRadius = circleRadius;
    }

    public float getCircleInterpolator() {
        return mCircleInterpolator;
    }

    public void setCircleInterpolator(float circleInterpolator) {
        this.mCircleInterpolator = circleInterpolator;
    }

    public float getMaskInterpolator() {
        return mMaskInterpolator;
    }

    public void setMaskInterpolator(float interpolator) {
        this.mMaskInterpolator = interpolator;
    }


    public float getCircleSchedule() {
        return mCircleSchedule;
    }

    public void setCircleSchedule(float circleSchedule) {
        this.mCircleSchedule = circleSchedule;
    }

    public Bitmap getMaskBitmap() {
        return mMaskBitmap;
    }

    public void setMaskBitmap(Bitmap maskBitmap) {
        this.mMaskBitmap = maskBitmap;
        updateMaskBitmap();
    }

}
