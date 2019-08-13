package com.ahyoxsoft.remoteresourceloader.di.module;

import android.app.Application;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;

import java.util.ArrayList;
import java.util.List;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public List<ResourceInfo> provideResourceInfos() {
        return new ArrayList<>();
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application);
    }

    @Provides
    public DefaultItemAnimator provideDefaultItemAnimator() {
        return new DefaultItemAnimator();
    }
}
