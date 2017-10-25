package ru.zaochno.zaochno.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.LinkedList;

import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.ui.holder.MaterialPhotoListViewHolder;

public class MaterialPhotoListAdapter extends RecyclerView.Adapter<MaterialPhotoListViewHolder> {
    private static final String TAG = "MaterialPhotoAdapter";

    private LinkedList<String> urls;
    private Context context;
    private OnImageClickListener onImageClickListener;

    public MaterialPhotoListAdapter(LinkedList<String> urls, Context context) {
        this.urls = urls;
        this.context = context;
    }

    public MaterialPhotoListAdapter(LinkedList<String> urls, Context context, OnImageClickListener onImageClickListener) {
        this.urls = urls;
        this.context = context;
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public MaterialPhotoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MaterialPhotoListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(final MaterialPhotoListViewHolder holder, int position) {
        if (urls == null)
            return;

        final String url = urls.get(position);

        if (url == null)
            return;

        if (onImageClickListener != null)
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageClickListener.onImageClick(url);
                }
            });

        Picasso.with(context)
                .load(url)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressView.setVisibility(View.GONE);
                    }
                });
    }

    public void setUrls(LinkedList<String> urls) {
        this.urls.clear();
        this.urls.addAll(urls);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public LinkedList<String> getUrls() {
        return urls;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener {
        void onImageClick(String url);
    }
}
