package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import com.gome.drawbitmaplib.BitmapInfo;

/**
 * Created by weijiaqi on 2017/7/23.
 */

public class BaseBitmapSateProgress extends BaseBitmapState{

    protected final static Point sOriginalPoint = new Point(0, 0);
    protected Rect mRendererRect;
    protected Point mViewCenterPoint;
    protected float mCircleRadius;
    protected float mCircleSchedule;
    private  float mMaskInterpolator;
    private  float mCircleInterpolator;



    protected Canvas mCanvas;


    public BaseBitmapSateProgress(BitmapInfo bitmapInfo) {
        super(bitmapInfo);

        mRendererRect = bitmapInfo.getRendererRect();
        mViewCenterPoint = bitmapInfo.getCenterPoint();
        mCanvas = new Canvas(mRendererBitmap);
        mCircleInterpolator = bitmapInfo.getCircleInterpolator();
        mMaskInterpolator = bitmapInfo.getMaskInterpolator();
        mCircleRadius = bitmapInfo.getCircleRadius() * mCircleInterpolator;

        mCircleSchedule = -360 * ((100 - (bitmapInfo.getCircleSchedule() * 3 / 4)) / 100 );

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

        mDrawPaint.setColor(Color.parseColor("#9A191919"));
        mCanvas.drawPath(path, mDrawPaint);
    }


    protected void drawMaskIcon() {
        if (mMaskInterpolator <= 0) {
            return;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(mMaskInterpolator, mMaskInterpolator);
        Bitmap reSizeBmp = Bitmap.createBitmap(mBitmapInfo.getMaskBitmap(),
                sOriginalPoint.x,
                sOriginalPoint.y,
                mRendererRect.width(),
                mRendererRect.height(), matrix, true);
        Log.e("wjq","mRendererRect.width() = " + mRendererRect.width() + "mRendererRect.height() = " + mRendererRect.height());
        mDrawPaint.setColor(Color.parseColor("#191919"));
        mCanvas.drawBitmap(reSizeBmp,
                    (int) (mRendererRect.width() * (1 - mMaskInterpolator) / 2),
                    (int) (mRendererRect.height() * (1 - mMaskInterpolator) / 2), mDrawPaint);
    }



}
