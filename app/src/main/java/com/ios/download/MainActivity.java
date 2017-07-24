package com.ios.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ios.downloadlibrary.StatusBitmap;
import com.ios.downloadlibrary.animation.AnimatorListener;
import com.ios.downloadlibrary.animation.DownloadAnimation;

public class MainActivity extends AppCompatActivity {
    DownloadAnimation mDownloadAnimation;
    BubbleTextView mBubbleTextView;
    StatusBitmap mStatusBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadAnimation = new DownloadAnimation();

        mBubbleTextView = (BubbleTextView) findViewById(R.id.bubble_text_view);

        mStatusBitmap = mBubbleTextView.getStatusBitmap();

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
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pictures);
            mStatusBitmap.setIconBitmap(bitmap);
            mStatusBitmap.setStatus(StatusBitmap.ICON_READY);
            mBubbleTextView.setStatusBitmap(mStatusBitmap);

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
                    mStatusBitmap.setCircleInterpolator(interpolator);
                    mStatusBitmap.setMaskInterpolator(interpolator);
                    mStatusBitmap.setStatus(StatusBitmap.PRE_DOWNLOAD);
                    mBubbleTextView.setStatusBitmap(mStatusBitmap);
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
            mStatusBitmap.setCircleSchedule(values[0]);
            mStatusBitmap.setStatus(StatusBitmap.DOWNLOADING);
            mBubbleTextView.setStatusBitmap(mStatusBitmap);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mStatusBitmap.setStatus(StatusBitmap.DOWNLOADED);
            mBubbleTextView.setStatusBitmap(mStatusBitmap);
            new InstallAsyncTask().execute(500);
        }

    }

    private class InstallAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStatusBitmap.setStatus(StatusBitmap.PRE_INSTALL);
            mBubbleTextView.setStatusBitmap(mStatusBitmap);
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
            mStatusBitmap.setCircleSchedule(values[0]);
            mStatusBitmap.setStatus(StatusBitmap.INSTALLING);
            mBubbleTextView.setStatusBitmap(mStatusBitmap);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mDownloadAnimation.startInstalledAnimation(new AnimatorListener() {
                @Override
                public void onUpdateListener(float interpolator) {
                    mStatusBitmap.setMaskInterpolator(interpolator);
                    mStatusBitmap.setStatus(StatusBitmap.INSTALLED);
                    mBubbleTextView.setStatusBitmap(mStatusBitmap);
                }
            });
        }
    }

}

