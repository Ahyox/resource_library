package com.ahyoxsoft.remoteresourceloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<InputStream, Void, Bitmap> {
    private Listener listener;

    public DownloadImageTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(InputStream... inputStreams) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(inputStreams[0]);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            listener.onImageDownloaded(result);
        } else {
            listener.onImageDownloadError();
        }
    }

    public static interface Listener {
        void onImageDownloaded(final Bitmap bitmap);
        void onImageDownloadError();
    }
}
