package com.ahyoxsoft.remoteresourceloader.model;

import androidx.lifecycle.LiveData;

import com.ahyoxsoft.downloader.request.Request;
import com.ahyoxsoft.downloader.util.DownloadListener;
import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;
import com.ahyoxsoft.remoteresourceloader.util.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

public class ModelLiveData extends LiveData<List<ResourceInfo>> {
    private Request resource;
    private Utility utility;

    @Inject
    public ModelLiveData(Request resource, Utility utility) {
        this.resource = resource;
        this.utility = utility;
    }

    @Override
    protected void onActive() {
        super.onActive();
        try {
            resource.subscribe(listener, "001");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        resource.cancelAll();
    }

    DownloadListener listener = new DownloadListener() {
        @Override
        public void onCompleted(InputStream inputStream) {
            try {
                List<ResourceInfo> info = utility.readJsonStream(inputStream);

                if (info != null)
                    setValue(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    };
}
