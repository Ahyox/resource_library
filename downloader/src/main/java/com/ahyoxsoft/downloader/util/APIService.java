package com.ahyoxsoft.downloader.util;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIService {

    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFile(@Url String fileName);

    @GET
    Observable<Response<ResponseBody>> getJson(@Url String path);
}

