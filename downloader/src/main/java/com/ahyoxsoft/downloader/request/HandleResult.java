package com.ahyoxsoft.downloader.request;

import android.util.Log;

import com.ahyoxsoft.downloader.util.DownloadListener;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class HandleResult extends DisposableObserver<Response<ResponseBody>> {
    private final String TAG = getClass().getSimpleName();
    private DownloadListener listener;

    public HandleResult(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void onNext(Response<ResponseBody> responseBody) {
        if (listener != null) {
            listener.onCompleted(responseBody.body().byteStream());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (listener != null)
            listener.onError(e);
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onCompleted");
    }


}
