package com.ahyoxsoft.remoteresourceloader;

import android.content.Context;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;
import com.ahyoxsoft.remoteresourceloader.model.ResourceAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ModelTest {
    private List<ResourceInfo> resourceInfos;
    private ResourceAdapter resourceAdapter;

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getContext();
        resourceInfos = new ArrayList<>();
        resourceAdapter = new ResourceAdapter(resourceInfos, appContext);
    }

    @Test
    public void resourceSize() {
        resourceInfos.add(new ResourceInfo("1", "2"));
        resourceInfos.add(new ResourceInfo());
        resourceInfos.add(new ResourceInfo("1", "2", "0"));

        assertEquals(resourceInfos.size(), 3);
    }

    @Test
    public void resourceContent() {
        resourceInfos.add(new ResourceInfo("1", "2"));
        resourceInfos.add(new ResourceInfo());
        resourceInfos.add(new ResourceInfo("1", "2", "0"));

        assertEquals(resourceInfos.get(0).getFileName(), "2");
    }

    @Test
    public void resourceIdGenerate() {
        resourceInfos.add(new ResourceInfo("1", "2"));
        resourceInfos.add(new ResourceInfo());
        resourceInfos.add(new ResourceInfo("1", "2", "0"));

        assertNotEquals(resourceInfos.get(1).getResourceId(), "2");
    }

    @Test
    public void adapter() {
        resourceInfos.add(new ResourceInfo("1", "2"));
        resourceInfos.add(new ResourceInfo());
        resourceInfos.add(new ResourceInfo("1", "2", "0"));

        resourceAdapter.notifyDataSetChanged();
        assertEquals(resourceAdapter.getItemCount(), 3);
    }
}
