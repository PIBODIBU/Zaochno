package ru.zaochno.zaochno.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

@SuppressLint("RestrictedApi")
public class PicassoTargetDrawable extends DrawableWrapper implements Target {
    private Context context;

    public PicassoTargetDrawable(Context context) {
        super(new ColorDrawable(Color.TRANSPARENT));

        // Use application context to not leak activity
        this.context = context.getApplicationContext();
    }

    public void onBitmapFailed(Drawable errorDrawable) {
//        setWrappedDrawable(errorDrawable);
//        invalidateSelf();
    }

    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setWrappedDrawable(new BitmapDrawable(context.getResources(), bitmap));
        getWrappedDrawable().setBounds(0, 0, DPPXUtils.dpToPx(context, 50), DPPXUtils.dpToPx(context, 50));
        context = null;
        invalidateSelf();
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
//        setWrappedDrawable(placeHolderDrawable);
//        invalidateSelf();
    }
}
