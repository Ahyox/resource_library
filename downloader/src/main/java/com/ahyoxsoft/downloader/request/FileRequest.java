package com.ahyoxsoft.downloader.request;

import android.content.Context;

import com.ahyoxsoft.downloader.di.components.DaggerDownloaderComponent;
import com.ahyoxsoft.downloader.util.DownloadListener;
import com.ahyoxsoft.downloader.di.components.DownloaderComponent;
import com.ahyoxsoft.downloader.di.modules.ContextModule;
import com.ahyoxsoft.downloader.di.modules.RetrofitClientModule;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * This object process request for a file
 */
public class FileRequest implements Request {
    private ReplaySubject<Response<ResponseBody>> subject;
    private Map<String, Disposable> disposables = new HashMap<>();



    public FileRequest(Context context, String baseUrl, String fileName) {
        Observable observable = getObservable(context, baseUrl, fileName);
        subject = ReplaySubject.create();

        observable.subscribe(subject);
    }

    private Observable getObservable(Context context, String baseUrl, String fileName) {
        DownloaderComponent downloaderComponent = DaggerDownloaderComponent.builder()
                .contextModule(new ContextModule(context))
                .retrofitClientModule(new RetrofitClientModule(baseUrl))
                .build();

        Observable observable = downloaderComponent.getService().downloadFile(fileName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    /**
     * Subscribe to the downloading resource
     * @param listener
     * @param listenerId a unique id for each subscription made. E.g UUID.randomUUID().toString()
     */
    public void subscribe(DownloadListener listener, String listenerId) throws Exception {
        if (!disposables.containsKey(listenerId)) {
            Disposable disposable = subject.subscribeWith(new HandleResult(listener));

            disposables.put(listenerId, disposable);
        } else {
            throw new Exception("Listener ID already exist");
        }
    }

    @Override
    public void cancel(String listenerId) {
        Disposable disposable = disposables.get(listenerId);
        if (!disposable.isDisposed()) {
            disposable.dispose();
            disposables.remove(listenerId);
        }
    }

    @Override
    public void cancelAll() {
        for (Map.Entry<String, Disposable> d : disposables.entrySet()) {
            Disposable disposable = d.getValue();
            if (!disposable.isDisposed()) {
                disposable.dispose();
                disposables.remove(d.getKey());
            }
        }
    }
}
