package com.ahyoxsoft.downloader.request;

import android.content.Context;
import com.ahyoxsoft.downloader.di.components.DaggerDownloaderComponent;
import com.ahyoxsoft.downloader.di.components.DownloaderComponent;
import com.ahyoxsoft.downloader.di.modules.ContextModule;
import com.ahyoxsoft.downloader.di.modules.RetrofitClientModule;
import com.ahyoxsoft.downloader.util.DownloadListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * This object process JSON request
 */
public class JsonRequest implements Request {
    private ReplaySubject<Response<ResponseBody>> subject;
    private Map<String, Disposable> disposables = new HashMap<>();

    public JsonRequest(Context context, String baseUrl, String path) {

        DownloaderComponent downloaderComponent = DaggerDownloaderComponent.builder()
                .contextModule(new ContextModule(context))
                .retrofitClientModule(new RetrofitClientModule(baseUrl))
                .build();

        Observable observable = downloaderComponent.getService().getJson(path)
                //.flatMap(processResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        subject = ReplaySubject.create();

        observable.subscribe(subject);
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

    private Function<Response<ResponseBody>, Observable<JSONObject>> processResponse() {
        return new Function<Response<ResponseBody>, Observable<JSONObject>>() {
            @Override
            public Observable<JSONObject> apply(Response<ResponseBody> responseBodyResponse) throws Exception {
                return process(responseBodyResponse);
            }
        };
    }

    private Observable<JSONObject> process(final Response<ResponseBody> response) {
        return Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(ObservableEmitter<JSONObject> emitter) {
                if (!emitter.isDisposed()) {
                    try (InputStream bis = new BufferedInputStream(response.body().byteStream(), 1024 * 8)){
                        String response = getStringFromResponse(bis);

                        System.out.println("RESOURCE: ERRR 1"+response);



                        JsonParser parser = new JsonParser();
                        JsonObject json = (JsonObject) parser.parse(response);
                        //JSONObject jsonObject = new JSONObject(request);

                        System.out.println("RESOURCE: ERRR 2"+json);
                        //emitter.onNext(jsonObject);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            }
        });
    }

    private String getStringFromResponse(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}

