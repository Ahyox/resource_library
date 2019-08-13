package com.ahyoxsoft.remoteresourceloader.di.module;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.ahyoxsoft.downloader.request.FileRequest;
import com.ahyoxsoft.downloader.request.JsonRequest;
import com.ahyoxsoft.remoteresourceloader.model.ResourceViewModel;
import com.ahyoxsoft.remoteresourceloader.model.ModelLiveData;
import com.ahyoxsoft.remoteresourceloader.util.Config;
import com.ahyoxsoft.remoteresourceloader.util.Utility;
import com.ahyoxsoft.remoteresourceloader.util.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class ViewModelModule {

    @Provides
    JsonRequest provideJsonRequest(Application application){
        return new JsonRequest(application, Config.BASE_URL, Config.PATH_URL);
    }

    @Provides
    Utility provideUtility() {
        return new Utility();
    }

    @Provides
    ModelLiveData provideModelLiveData(JsonRequest resource, Utility utility) {
        return new ModelLiveData(resource, utility);
    }

    @Provides
    ResourceViewModel provideResourceViewModel(ModelLiveData modelLiveData) {
        return new ResourceViewModel(modelLiveData);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelProvider(ResourceViewModel viewModel){
        return new ViewModelProviderFactory<>(viewModel);
    }
}
