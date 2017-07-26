package com.gome.drawbitmaplib.bitmapState;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    private  float mTransLateCircleInterpolator;
    private  float mCircleInterpolator;
    private float mTransLateCircleRadius;



    protected Canvas mCanvas;


    public BaseBitmapSateProgress(BitmapInfo bitmapInfo) {
        super(bitmapInfo);

        mRendererRect = bitmapInfo.getRendererRect();
        mViewCenterPoint = bitmapInfo.getCenterPoint();
        mCanvas = new Canvas(mRendererBitmap);
        mCircleInterpolator = bitmapInfo.getCircleInterpolator();
        mTransLateCircleInterpolator = bitmapInfo.getTransLateInterpolator();
        mCircleRadius = bitmapInfo.getCircleRadius() * mCircleInterpolator;
        mTransLateCircleRadius = (mCircleRadius + 10) * mTransLateCircleInterpolator;
        mCircleSchedule = -360 * ((100 - (bitmapInfo.getCircleSchedule() * 3 / 4)) / 100 );

    }

    @Override
    public Bitmap decodeBitmap() {
        int canvasWidth = mCanvas.getWidth();
        int canvasHeight = mCanvas.getHeight();
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        mCanvas.clipRect(0,0,mRendererRect.width(),mRendererRect.height());

        drawMaskIcon();
        drawTransLateCircle();
        drawArc();

        mCanvas.restoreToCount(layerId);
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
        float maskInterpolator = 1f;
        if (maskInterpolator <= 0) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(maskInterpolator, maskInterpolator);
        Bitmap reSizeBmp = Bitmap.createBitmap(mBitmapInfo.getMaskBitmap(),
                sOriginalPoint.x,
                sOriginalPoint.y,
                mRendererRect.width(),
                mRendererRect.height(), matrix, true);
        mDrawPaint.setColor(Color.parseColor("#191919"));
        mCanvas.drawBitmap(reSizeBmp,
                    (int) (mRendererRect.width() * (1 - maskInterpolator) / 2),
                    (int) (mRendererRect.height() * (1 - maskInterpolator) / 2), mDrawPaint);
    }


    protected void drawTransLateCircle() {
        mDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawCircle(mViewCenterPoint.x, mViewCenterPoint.y, mTransLateCircleRadius,mDrawPaint);
        mDrawPaint.setXfermode(null);

    }



}
