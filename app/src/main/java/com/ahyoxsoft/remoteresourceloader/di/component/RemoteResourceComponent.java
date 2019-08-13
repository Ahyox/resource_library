package com.ahyoxsoft.remoteresourceloader.di.component;

import android.app.Application;

import com.ahyoxsoft.remoteresourceloader.di.module.ActivityBuilderModule;
import com.ahyoxsoft.remoteresourceloader.di.module.AppModule;
import com.ahyoxsoft.remoteresourceloader.util.RemoteResourceApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBuilderModule.class, AppModule.class})
public interface RemoteResourceComponent extends AndroidInjector<RemoteResourceApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        RemoteResourceComponent build();
    }
}
