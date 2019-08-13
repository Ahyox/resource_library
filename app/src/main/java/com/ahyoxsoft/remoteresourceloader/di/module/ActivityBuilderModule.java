package com.ahyoxsoft.remoteresourceloader.di.module;

import com.ahyoxsoft.remoteresourceloader.view.activity.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {AppModule.class, ViewModelModule.class})
    abstract MainActivity contributesMainActivity();

}
