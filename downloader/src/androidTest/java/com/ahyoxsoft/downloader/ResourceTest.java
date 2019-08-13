package com.ahyoxsoft.downloader;

import android.content.Context;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import com.ahyoxsoft.downloader.request.FileRequest;
import com.ahyoxsoft.downloader.request.Request;
import com.ahyoxsoft.downloader.util.DownloadListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.InputStream;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ResourceTest {

    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private String baseUrl = "https://images.pexels.com/photos/67636/";
    private String fileName = "rose-blue-flower-rose-blooms-67636.jpeg";
    private Request resourceFile;

    @Before
    public void init() {
        resourceFile = new FileRequest(appContext, baseUrl, fileName);
    }

    @Test
    public void subscribe() throws Exception {
        resourceFile.subscribe(new DownloadListener() {
            @Override
            public void onCompleted(InputStream inputStream) {
                assertNotNull(inputStream);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, "001");
    }

    @After
    public void unsubscribe() {
        resourceFile.cancel("001");
    }
}
