package com.ahyoxsoft.remoteresourceloader.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahyoxsoft.downloader.request.FileRequest;
import com.ahyoxsoft.downloader.request.Request;
import com.ahyoxsoft.downloader.util.DownloadListener;
import com.ahyoxsoft.remoteresourceloader.R;
import com.ahyoxsoft.remoteresourceloader.data.pojo.ResourceInfo;
import com.ahyoxsoft.remoteresourceloader.util.DownloadImageTask;

import java.io.InputStream;
import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceHolder> {
    private List<ResourceInfo> resourceInfoList;
    private Context context;

    public ResourceAdapter(List<ResourceInfo> resourceInfos, Context context) {
        this.resourceInfoList = resourceInfos;
        this.context = context;
    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.download_card_layout, viewGroup, false);
        return new ResourceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ResourceHolder resourceHolder, int i) {
        final ResourceInfo resourceInfo = resourceInfoList.get(i);

        if (resourceInfo.getBaseUrl() != null) {

            final Request request = new FileRequest(context, resourceInfo.getBaseUrl(), resourceInfo.getFileName());
            try {
                request.subscribe(new DownloadListener() {
                    @Override
                    public void onCompleted(InputStream inputStream) {

                        new DownloadImageTask(new DownloadImageTask.Listener() {
                            @Override
                            public void onImageDownloaded(Bitmap bitmap) {
                                resourceHolder.imageView.setImageBitmap(bitmap);
                                resourceHolder.progressBar.setIndeterminate(false);
                            }

                            @Override
                            public void onImageDownloadError() {

                            }
                        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, inputStream);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }, resourceInfo.getResourceId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            resourceHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.cancel(resourceInfo.getResourceId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return resourceInfoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ResourceHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView cancelBtn;
        private ProgressBar progressBar;

        public ResourceHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(InputStream inputStream) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 100, 100);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }
}
