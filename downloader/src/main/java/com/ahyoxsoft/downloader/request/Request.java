package com.ahyoxsoft.downloader.request;


import com.ahyoxsoft.downloader.util.DownloadListener;

public interface Request {


    /**
     * Subscribe to the downloading resource
     * @param listener
     * @param listenerId a unique id for each subscription made. E.g UUID.randomUUID().toString()
     */
    void subscribe(DownloadListener listener, String listenerId) throws Exception;


    /**
     * Cancel subscription from resource
     * @param listenerId - Unique id of the listener to cancel
     */
    void cancel(String listenerId);


    /**
     * Cancel all subscription done on this resource
     */
    void cancelAll();
}
