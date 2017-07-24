package com.ios.downloadlibrary.bitmapState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.ios.downloadlibrary.StatusBitmap;

/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BaseBitmapSateProgress extends BaseBitmapState{

    protected final static Point sOriginalPoint = new Point(0, 0);

    protected Point mRendererViewPoint;
    protected Point mViewCenterPoint;
    protected float mCircleRadius;
    protected float mCircleSchedule;
    private  float mMaskInterpolator;
    private  float mCircleInterpolator;


    protected Canvas mCanvas;
    protected Paint mDrawPaint;

    public BaseBitmapSateProgress(StatusBitmap statusBitmap) {
        super(statusBitmap);
        mCanvas = new Canvas(mRendererBitmap);
        mCanvas.clipRect(mStatusBitmap.getBounds());
        mRendererViewPoint = statusBitmap.getRendererPoint();
        mViewCenterPoint = statusBitmap.getCenterPoint();
        mCircleInterpolator = statusBitmap.getCircleInterpolator();
        mMaskInterpolator = statusBitmap.getMaskInterpolator();
        mCircleRadius = statusBitmap.getCircleRadius() * mCircleInterpolator;

        mDrawPaint = statusBitmap.getDefaultPaint();
        mDrawPaint.setColor(Color.parseColor("#9A000000"));

        mCircleSchedule = -360 * ((100 - (mStatusBitmap.getCircleSchedule() * 3 / 4)) / 100 );
    }

    @Override
    public Bitmap decodeBitmap() {
        drawArc();
        drawMaskIcon();
        return mRendererBitmap;
    }


    protected void drawArc() {
        RectF ovalRectF = new RectF(
                mViewCenterPoint.x - mCircleRadius,
                mViewCenterPoint.y - mCircleRadius,
                mViewCenterPoint.x + mCircleRadius,
                mViewCenterPoint.y + mCircleRadius);

        Path path = new Path();
        path.moveTo(mViewCenterPoint.x, mViewCenterPoint.y);
        path.lineTo(mViewCenterPoint.x + mCircleRadius, mViewCenterPoint.y);
        path.addArc(ovalRectF, 270, mCircleSchedule);
        path.lineTo(mViewCenterPoint.x, mViewCenterPoint.y);
        path.close();
        mCanvas.drawPath(path, mDrawPaint);
    }


    protected void drawMaskIcon() {
        if (mMaskInterpolator <= 0) {
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(mMaskInterpolator, mMaskInterpolator);
        Bitmap reSizeBmp = Bitmap.createBitmap(mStatusBitmap.getMaskBitmap(),
                sOriginalPoint.x,
                sOriginalPoint.y,
                mRendererViewPoint.x,
                mRendererViewPoint.y, matrix, true);

        mCanvas.drawBitmap(reSizeBmp,
                (int) (mRendererViewPoint.x * (1 - mMaskInterpolator) / 2),
                (int) (mRendererViewPoint.y * (1 - mMaskInterpolator) / 2), mDrawPaint);
    }



}
