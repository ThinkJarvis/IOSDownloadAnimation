package com.gome.drawbitmaplib.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Created by weijiaqi on 2017/7/22.
 */

public class DownloadAnimation {
    private static  DownloadAnimation sDownloadAnimation;

    private float interpolator = 0f;
    private static final float[] PRE_DOWNLOAD = new float[]{0.5f,1.00f};
    private static final  float[] INSTALLED =new float[]{1f,2.2f};
    private static final long CIRCLE_DURATION = 500;
    private static final long MASK_DURATION = 800;

    public static DownloadAnimation getInstance() {
        if (null == sDownloadAnimation) {
            sDownloadAnimation = new DownloadAnimation();
        }
        return sDownloadAnimation;
    }

    public void startPreDownLoadAnimation(final AnimatorListener animatorListener) {
        startAnimation(PRE_DOWNLOAD,animatorListener,CIRCLE_DURATION);
    }

    public void startInstalledAnimation(final AnimatorListener animatorListener) {
        startAnimation(INSTALLED,animatorListener,MASK_DURATION);
    }

    private void startAnimation(float[] point,final AnimatorListener animatorListener,long duration) {
        ObjectAnimator scaleXY = ObjectAnimator.ofFloat(this,"interpolator",point[0],point[1]);
        scaleXY.setInterpolator(new LinearInterpolator());
        scaleXY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (null != animatorListener) {
                    animatorListener.onUpdateListener(getInterpolator());
                }
            }
        });
        scaleXY.setDuration(duration);
        scaleXY.start();
    }


    public float getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(float interpolator) {
        this.interpolator = interpolator;
    }

}



