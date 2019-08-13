package com.ahyoxsoft.downloader.di.components;

import com.ahyoxsoft.downloader.di.modules.RetrofitClientModule;
import com.ahyoxsoft.downloader.util.APIService;

import dagger.Component;

@Component(modules = {RetrofitClientModule.class})
public interface DownloaderComponent {

    APIService getService();
}
