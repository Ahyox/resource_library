package com.ahyoxsoft.remoteresourceloader.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResourceViewModel extends ViewModel {
    private MediatorLiveData<List<ResourceInfo>> resourceMediatorLiveData = new MediatorLiveData<>();
    private ModelLiveData modelLiveData;
    private final int OFFSET = 10;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private final String TAG = getClass().getSimpleName();

    public ResourceViewModel(ModelLiveData modelLiveData) {
        this.modelLiveData = modelLiveData;

        onLoad();
    }

    public void onLoad() {
        resourceMediatorLiveData.addSource(modelLiveData, new Observer<List<ResourceInfo>>() {
            @Override
            public void onChanged(List<ResourceInfo> resourceInfoList) {
                resourceMediatorLiveData.postValue(resourceInfoList);
            }
        });
    }

    public void onLoadMore() {

    }

    public LiveData<List<ResourceInfo>> getData() {
        return resourceMediatorLiveData;
    }
}
