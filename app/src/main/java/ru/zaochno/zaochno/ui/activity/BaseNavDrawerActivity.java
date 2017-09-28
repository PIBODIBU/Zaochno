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
import ru.zaochno.zaochno.data.provider.AuthProvider;
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

    private int stickyFooterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefUtils = new SharedPrefUtils(this);
        currentUser = AuthProvider.getInstance(this).getCurrentUser();

        stickyFooterId = AuthProvider.getInstance(this).isAuthenticated() ? R.layout.drawer_footer : -1;
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

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header)
                .withTranslucentStatusBar(false)
                .withStickyFooter(stickyFooterId)
                .withStickyFooterShadow(false)
                .withActionBarDrawerToggle(true)
                .build();

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        if (AuthProvider.getInstance(this).isAuthenticated())
            drawer.addItems(
                    createItem(R.drawable.ic_training, "Мои тренинги", true, TrainingListActivity.class, true),
                    createItem(R.drawable.ic_favourite, "Избранное", true, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(BaseNavDrawerActivity.this, TrainingListActivity.class)
                                    .putExtra(TrainingListActivity.INTENT_KEY_TAB, TrainingListActivity.INTENT_KEY_TAB_FAVOURITE));
                            finish();
                        }
                    }),
                    createItem(R.drawable.ic_testing, "Тестирование", true, null, true),
                    createItem(R.drawable.ic_email, "Мои сообщения", true, MessageListActivity.class, true),
                    createItem(R.drawable.ic_settings, "Настройка", true, null, true),
                    createItem(R.drawable.ic_exit, "Выход", false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AuthProvider.getInstance(BaseNavDrawerActivity.this).logOut();
                            startActivity(new Intent(BaseNavDrawerActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
            );
        else
            drawer.addItems(
                    createTextItem("Для полноценного пользования всему функциями приложения ZaOchno.Ru, пожалуйста, авторизируйтесь или зарегистрируйтесь", true),
                    createItem(R.drawable.ic_favourite, "Авторизация", true, LoginActivity.class, true),
                    createItem(R.drawable.ic_training, "Тренинги", true, TrainingListActivity.class, true)
            );

        setupHeader(drawer.getHeader());

        if (AuthProvider.getInstance(this).isAuthenticated())
            setupFooter(drawer.getStickyFooter());

        drawer.setSelection(-1);
    }

    private ContainerDrawerItem createTextItem(@NonNull String text, Boolean showDivider) {
        View v = LayoutInflater.from(this).inflate(R.layout.drawer_item_text, null);
        ((TextView) v.findViewById(R.id.tv_text)).setText(text);

        return new ContainerDrawerItem()
                .withView(v)
                .withDivider(showDivider);
    }

    private ContainerDrawerItem createItem(@DrawableRes int iconRes, @NonNull String title, Boolean showDivider, View.OnClickListener itemClickListener) {
        View v = LayoutInflater.from(this).inflate(R.layout.drawer_item, null);
        Picasso.with(this)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        v.setOnClickListener(itemClickListener);
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        return new ContainerDrawerItem()
                .withView(v)
                .withHeight(DimenHolder.fromDp(60))
                .withDivider(showDivider);
    }

    private ContainerDrawerItem createItem(@DrawableRes int iconRes, @NonNull String title, Boolean showDivider, final Class activityToStart, final Boolean finishThis) {
        View v = LayoutInflater.from(this).inflate(R.layout.drawer_item, null);
        Picasso.with(this)
                .load(iconRes)
                .into(((ImageView) v.findViewById(R.id.iv_icon)));
        ((TextView) v.findViewById(R.id.tv_title)).setText(title);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityToStart == null)
                    return;

                startActivity(new Intent(BaseNavDrawerActivity.this, activityToStart));
                if (finishThis)
                    BaseNavDrawerActivity.this.finish();
            }
        });

        return new ContainerDrawerItem()
                .withView(v)
                .withHeight(DimenHolder.fromDp(60))
                .withDivider(showDivider);
    }

    private void setupHeader(View header) {
        Picasso.with(this)
                .load(R.drawable.account_header_bg)
                .into(((ImageView) header.findViewById(R.id.iv_header_bg)));

        Picasso.with(this)
                .load(R.drawable.logo)
                .into(((ImageView) header.findViewById(R.id.iv_logo)));

        if (AuthProvider.getInstance(this).isAuthenticated())
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
