package com.ios.download;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gome.drawbitmaplib.BitmapInfo;
import com.gome.drawbitmaplib.animation.AnimatorListener;
import com.gome.drawbitmaplib.animation.DownloadAnimation;


public class MainActivity extends AppCompatActivity {
    DownloadAnimation mDownloadAnimation;
    BubbleTextView mBubbleTextView;
    BitmapInfo mBitmapInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadAnimation = new DownloadAnimation();

        mBubbleTextView = (BubbleTextView) findViewById(R.id.bubble_text_view);

        mBitmapInfo = mBubbleTextView.getBitmapInfo();

        Button download = (Button) findViewById(R.id.download);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadImageAsyncTask().execute(1000 * 2);
            }
        });


    }

    private class DownloadImageAsyncTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBitmapInfo.setStatus(BitmapInfo.ICON_READY);
            mBubbleTextView.setBitmapInfo(mBitmapInfo);

        }

        @Override
        protected Boolean doInBackground(Integer... params) {
                try {
                    Thread.sleep(params[0]);
                    publishProgress(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            new DownloadAPKAsyncTask().execute(50);




        }

    }

    private class DownloadAPKAsyncTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDownloadAnimation.startPreDownLoadAnimation(new AnimatorListener() {
                @Override
                public void onUpdateListener(float interpolator) {
                    mBitmapInfo.setCircleInterpolator(interpolator);
                    mBitmapInfo.setTransLateInterpolator(1.0f);
                    mBitmapInfo.setStatus(BitmapInfo.PRE_DOWNLOAD);
                    mBubbleTextView.setBitmapInfo(mBitmapInfo);
                }
            });
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(params[0]);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mBitmapInfo.setCircleSchedule(values[0]);
            mBitmapInfo.setStatus(BitmapInfo.DOWNLOADING);
            mBubbleTextView.setBitmapInfo(mBitmapInfo);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mBitmapInfo.setStatus(BitmapInfo.DOWNLOADED);
            mBubbleTextView.setBitmapInfo(mBitmapInfo);
            new InstallAsyncTask().execute(500);
        }

    }

    private class InstallAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBitmapInfo.setStatus(BitmapInfo.PRE_INSTALL);
            mBubbleTextView.setBitmapInfo(mBitmapInfo);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(params[0]);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mBitmapInfo.setCircleSchedule(values[0]);
            mBitmapInfo.setStatus(BitmapInfo.INSTALLING);
            mBubbleTextView.setBitmapInfo(mBitmapInfo);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mDownloadAnimation.startInstalledAnimation(new AnimatorListener() {
                @Override
                public void onUpdateListener(float interpolator) {
                    mBitmapInfo.setTransLateInterpolator(interpolator);
                    mBitmapInfo.setStatus(BitmapInfo.INSTALLED);
                    mBubbleTextView.setBitmapInfo(mBitmapInfo);
                }
            });
        }
    }
}

