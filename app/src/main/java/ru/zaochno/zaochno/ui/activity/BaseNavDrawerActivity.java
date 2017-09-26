package ru.zaochno.zaochno.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.ContainerDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;

public class BaseNavDrawerActivity extends AppCompatActivity {
    private static final String TAG = "BaseNavDrawerActivity";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Drawer drawer;

    private Toolbar toolbar;
    private SharedPrefUtils sharedPrefUtils;
    private HashMap<String, IDrawerItem> drawerItems = new HashMap<>();
    private User currentUser;
    private View upFooter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefUtils = new SharedPrefUtils(this);
        currentUser = sharedPrefUtils.getCurrentUser();
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    protected void setupDrawer() {
        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                return null;
            }
        });

        /*PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.drawer_item_training_list)
                .withIcon(R.drawable.ic_training);

        drawerItems.put(TrainingListActivity.class.getName(), item1);*/

        drawer = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        createItem(R.drawable.ic_training, "Мои тренинги", true),
                        createItem(R.drawable.ic_favourite, "Избранное", true),
                        createItem(R.drawable.ic_testing, "Тестирование", true),
                        createItem(R.drawable.ic_email, "Мои сообщения", true),
                        createItem(R.drawable.ic_settings, "Настройка", true),
                        createItem(R.drawable.ic_exit, "Выход", false)
                )
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header)
                .withStickyFooter(R.layout.drawer_footer)
                .withStickyFooterShadow(false)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()) {
                            case 1:
                                startActivity(new Intent(BaseNavDrawerActivity.this, TrainingListActivity.class));
                                finish();
                                return true;
                        }

                        return true;
                    }
                })
                .build();

        drawer.getActionBarDrawerToggle()
                .setDrawerIndicatorEnabled(true);

        setupHeader(drawer.getHeader());
        setupFooter(drawer.getStickyFooter());

        drawer.setSelection(-1);
        //setDrawerSelection();
    }

    private ContainerDrawerItem createItem(@DrawableRes int iconRes, @NonNull String title, Boolean showDivider) {
        View v = LayoutInflater.from(this).inflate(R.layout.drawer_item, null);
        Picasso.with(this)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        return new ContainerDrawerItem().withView(v).withHeight(DimenHolder.fromDp(60)).withDivider(showDivider);
    }

    private void setupHeader(View header) {
        Picasso.with(this)
                .load(R.drawable.account_header_bg)
                .into(((ImageView) header.findViewById(R.id.iv_header_bg)));

        Picasso.with(this)
                .load(R.drawable.logo)
                .into(((ImageView) header.findViewById(R.id.iv_logo)));

        Picasso.with(this)
                .load(currentUser.getPhotoUrl())
                .into(((CircleImageView) header.findViewById(R.id.iv_user_avatar)));
    }

    private void setupFooter(View upFooter) {
        ((TextView) upFooter.findViewById(R.id.tv_user_name)).setText(currentUser.getName());

    }

    private void setDrawerSelection() {
        try {
            drawer.setSelection(drawerItems.get(this.getClass().getName()), false);
        } catch (Exception ex) {
            ex.printStackTrace();
            drawer.setSelection(-1);
        }
    }
}
