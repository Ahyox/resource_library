package com.ahyoxsoft.remoteresourceloader.util;




import com.ahyoxsoft.remoteresourceloader.di.component.DaggerRemoteResourceComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class RemoteResourceApplication extends DaggerApplication {

    private static RemoteResourceApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized RemoteResourceApplication getInstance() {
        return instance;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerRemoteResourceComponent.builder().application(this).build();
    }
}
