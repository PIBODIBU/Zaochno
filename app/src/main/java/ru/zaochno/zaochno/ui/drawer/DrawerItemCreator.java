package ru.zaochno.zaochno.ui.drawer;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.squareup.picasso.Picasso;

import ru.zaochno.zaochno.R;

public class DrawerItemCreator {
    public static ContainerDrawerItem createTextItem(final Activity activity,
                                                     @NonNull String text,
                                                     Boolean showDivider,
                                                     long id) {
        View v = LayoutInflater.from(activity).inflate(R.layout.drawer_item_text, null);
        ((TextView) v.findViewById(R.id.tv_text)).setText(text);

        return new ContainerDrawerItem()
                .withView(v)
                .withIdentifier(id)
                .withDivider(showDivider);
    }

    public static ContainerDrawerItem createItem(final Activity activity,
                                                 @DrawableRes int iconRes,
                                                 @NonNull String title,
                                                 long id,
                                                 Boolean showDivider,
                                                 View.OnClickListener itemClickListener) {
        View v = LayoutInflater.from(activity).inflate(R.layout.drawer_item, null);
        Picasso.with(activity)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        v.setOnClickListener(itemClickListener);
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        return new ContainerDrawerItem()
                .withView(v)
                .withIdentifier(id)
                .withHeight(DimenHolder.fromDp(60))
                .withDivider(showDivider);
    }

    public static ContainerDrawerItem createItem(final Activity activity,
                                                 @DrawableRes int iconRes,
                                                 @NonNull String title,
                                                 long id,
                                                 Boolean showDivider,
                                                 final Class activityToStart,
                                                 final Boolean finishThis) {
        View v = LayoutInflater.from(activity).inflate(R.layout.drawer_item, null);
        Picasso.with(activity)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityToStart == null)
                    return;

                activity.startActivity(new Intent(activity, activityToStart));
                if (finishThis)
                    activity.finish();
            }
        });

        return new ContainerDrawerItem()
                .withView(v)
                .withIdentifier(id)
                .withHeight(DimenHolder.fromDp(60))
                .withDivider(showDivider);
    }

    public static ContainerDrawerItem createItem(final Activity activity,
                                                 @DrawableRes int iconRes,
                                                 @LayoutRes int layoutRes,
                                                 @NonNull String title,
                                                 long id, Boolean showDivider,
                                                 final Class activityToStart,
                                                 final Boolean finishThis) {
        View v = LayoutInflater.from(activity).inflate(layoutRes, null);
        Picasso.with(activity)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityToStart == null)
                    return;

                activity.startActivity(new Intent(activity, activityToStart));
                if (finishThis)
                    activity.finish();
            }
        });

        return new ContainerDrawerItem()
                .withView(v)
                .withIdentifier(id)
                .withHeight(DimenHolder.fromDp(60))
                .withDivider(showDivider);
    }
}
