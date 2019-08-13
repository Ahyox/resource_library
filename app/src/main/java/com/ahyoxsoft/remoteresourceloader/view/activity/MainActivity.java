package com.ahyoxsoft.remoteresourceloader.view.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ahyoxsoft.remoteresourceloader.R;
import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;
import com.ahyoxsoft.remoteresourceloader.model.ResourceAdapter;
import com.ahyoxsoft.remoteresourceloader.base.BaseActivity;
import com.ahyoxsoft.remoteresourceloader.model.ResourceViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ResourceViewModel> {
    private RecyclerView recyclerView;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    ResourceAdapter resourceAdapter;

    @Inject
    List<ResourceInfo> resourceInfoList;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    DefaultItemAnimator defaultItemAnimator;

    @Inject
    ViewModelProvider.Factory factory;

    ResourceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);

        recyclerView.setItemAnimator(defaultItemAnimator);
        recyclerView.setLayoutManager(linearLayoutManager);
        resourceAdapter =  new ResourceAdapter(resourceInfoList, this);
        recyclerView.setAdapter(resourceAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    //loadingBar();
                    viewModel.onLoadMore();

                    loading = true;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getData().observe(this, new Observer<List<ResourceInfo>>() {
            @Override
            public void onChanged(List<ResourceInfo> resourceInfos) {
                resourceInfoList.addAll(resourceInfos);
                resourceAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public ResourceViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(ResourceViewModel.class);
        return viewModel;
    }
}

