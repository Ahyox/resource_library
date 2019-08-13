package com.ahyoxsoft.downloader;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.ahyoxsoft.downloader.di.components.DaggerDownloaderComponent;
import com.ahyoxsoft.downloader.di.components.DownloaderComponent;
import com.ahyoxsoft.downloader.di.modules.ContextModule;
import com.ahyoxsoft.downloader.di.modules.RetrofitClientModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class ExampleInstrumentedTest {
    // Context of the app under test.
    private Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    private String baseUrl = "https://images.pexels.com/photos/67636/";

    DownloaderComponent downloaderComponent;

    @Test
    public void useAppContext() {
        assertEquals("com.ahyoxsoft.downloader.test", appContext.getPackageName());
    }

    @Before
    public void init() {
        downloaderComponent = DaggerDownloaderComponent.builder()
                .contextModule(new ContextModule(appContext))
                .retrofitClientModule(new RetrofitClientModule(baseUrl))
                .build();
    }

    @Test
    public void serviceTest() {
        assertNotNull(downloaderComponent.getService());
    }



}
