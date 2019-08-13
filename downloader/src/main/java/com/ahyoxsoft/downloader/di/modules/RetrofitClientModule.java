package com.ahyoxsoft.downloader.di.modules;

import com.ahyoxsoft.downloader.util.APIService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class RetrofitClientModule {
    String baseUrl;

    public RetrofitClientModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    public APIService createService(Retrofit retrofit) {

        return retrofit.create(APIService.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

}
