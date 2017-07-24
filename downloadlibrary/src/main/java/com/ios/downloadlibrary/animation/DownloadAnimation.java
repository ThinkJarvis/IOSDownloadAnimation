package com.ios.downloadlibrary.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.animation.LinearInterpolator;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class DownloadAnimation {

    private float interpolator = 0f;
    private static final Point PRE_DOWNLOAD = new Point(0,1);
    private static final Point INSTALLED = new Point(1,2);

    public void startPreDownLoadAnimation(final AnimatorListener animatorListener) {
        startAnimation(PRE_DOWNLOAD,animatorListener);
    }

    public void startInstalledAnimation(final AnimatorListener animatorListener) {
        startAnimation(INSTALLED,animatorListener);
    }

    private void startAnimation(Point point,final AnimatorListener animatorListener) {
        ObjectAnimator scaleXY = ObjectAnimator.ofFloat(this,"interpolator",point.x,point.y);
        scaleXY.setInterpolator(new LinearInterpolator());
        scaleXY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (null != animatorListener) {
                    animatorListener.onUpdateListener(getInterpolator());
                }
            }
        });
        scaleXY.setDuration(500 * 1);
        scaleXY.start();
    }


    public float getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(float interpolator) {
        this.interpolator = interpolator;
    }
    
}

