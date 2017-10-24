package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.ui.holder.MaterialVideoListViewHolder;

public class MaterialVideoListAdapter extends RecyclerView.Adapter<MaterialVideoListViewHolder> {
    private static final String TAG = "MaterialVideoListAdapter";

    private LinkedList<String> urls;
    private Context context;
    private MaterialVideoListAdapter.OnImageClickListener onImageClickListener;


    public MaterialVideoListAdapter(Context context, MaterialVideoListAdapter.OnImageClickListener onImageClickListener) {
        this.urls = new LinkedList<>();
        this.context = context;
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public MaterialVideoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MaterialVideoListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_video, parent, false));
    }

    @Override
    public void onBindViewHolder(MaterialVideoListViewHolder holder, int position) {
        if (urls == null)
            return;

        final String url = urls.get(position);

        if (url == null)
            return;

        if (onImageClickListener != null)
            holder.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClickListener.onVideoClick(url);
                }
            });

        holder.ivThumbnail.setImageBitmap(retrieveVideoFrameFromVideo(url));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void clearUrls() {
        urls.clear();

        notifyDataSetChanged();
    }

    public void addUrl(String url) {
        if (url == null)
            return;

        this.urls.add(url);
        notifyDataSetChanged();
    }

    private Bitmap retrieveVideoFrameFromVideo(String videoPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public interface OnImageClickListener {
        void onVideoClick(String url);
    }
}
