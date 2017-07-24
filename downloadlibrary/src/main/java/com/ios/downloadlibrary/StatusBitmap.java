package com.ios.downloadlibrary;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class StatusBitmap {
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
    private Bitmap mIconBitmap;

    private Paint mDrawPaint;
    private Point mRendererPoint;
    private Point mCenterPoint;
    private int mStatus;
    private Rect mBounds;
    private float mMaskInterpolator;
    private float mCircleInterpolator;
    private float mCircleRadius;
    private float mCircleSchedule;




    public StatusBitmap(View view) {
        mStatus = NONE;
        setDefaultPaint();
        initConfig(view);
    }


    private void initConfig(View view) {
        if (view instanceof TextView) {
            Drawable textViewDrawable = getTextViewDrawable((TextView)view);
            mBounds = textViewDrawable.getBounds();
            mRendererPoint = new Point(mBounds.width(),
                    mBounds.height());


            mCenterPoint = new Point(mBounds.centerX(), mBounds.centerY());


            mCircleRadius =  Math.min(mRendererPoint.x, mRendererPoint.y) / 3;
        }
    }

    public Bitmap createRendererBitmap() {
        Bitmap rendererBitmap = null;
        if (mRendererPoint != null) {
            rendererBitmap = Bitmap.createBitmap(mRendererPoint.x,
                    mRendererPoint.y, Bitmap.Config.ARGB_8888);
        }
        return rendererBitmap;
    }

    public void setDefaultPaint() {
        mDrawPaint = new Paint();
        mDrawPaint.setStyle(Paint.Style.FILL);
        mDrawPaint.setAntiAlias(true);
    }

    public Paint getDefaultPaint() {
        return mDrawPaint;
    }


    private Drawable getTextViewDrawable(TextView textView) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            if (drawables[i] != null) {
                return drawables[i];
            }
        }
        return null;
    }

    private void updateMaskBitmap() {
        Matrix matrix = new Matrix();
        float scaleX = mMaskBitmap.getWidth() / mRendererPoint.x;
        float scaleY = mMaskBitmap.getHeight() / mRendererPoint.y;
        matrix.postScale(scaleX,scaleY);
        Bitmap temp = Bitmap.createBitmap(mMaskBitmap,0,0,mMaskBitmap.getWidth(),mMaskBitmap.getHeight(),matrix,true);
        mMaskBitmap = temp;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        this.mStatus = status;
    }

    public Point getRendererPoint() {
        return mRendererPoint;
    }

    public void setRendererPoint(Point rendererPoint) {
        this.mRendererPoint = rendererPoint;
    }

    public Point getCenterPoint() {
        return mCenterPoint;
    }

    public void setmCenterPoint(Point centerPoint) {
        this.mCenterPoint = centerPoint;
    }

    public Bitmap getIconBitmap() {
        return mIconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.mIconBitmap = iconBitmap;
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

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.mCircleRadius = circleRadius;
    }

    public float getCircleSchedule() {
        return mCircleSchedule;
    }

    public void setCircleSchedule(float circleSchedule) {
        this.mCircleSchedule = circleSchedule;
    }

    public Rect getBounds() {
        return mBounds;
    }

    public void setBounds(Rect bounds) {
        this.mBounds = bounds;
    }

    public Bitmap getMaskBitmap() {
        return mMaskBitmap;
    }

    public void setMaskBitmap(Bitmap maskBitmap) {
        this.mMaskBitmap = maskBitmap;
        updateMaskBitmap();

    }
}
