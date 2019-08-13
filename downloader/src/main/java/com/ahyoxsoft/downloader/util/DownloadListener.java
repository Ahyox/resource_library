package com.ahyoxsoft.downloader.util;


import java.io.InputStream;

public interface DownloadListener {
    void onCompleted(InputStream inputStream);
    void onError(Throwable e);
}
