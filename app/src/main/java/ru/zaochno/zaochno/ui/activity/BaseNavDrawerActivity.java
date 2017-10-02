package ru.zaochno.zaochno.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Message;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.model.request.TokenRequest;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.data.shared.SharedPrefUtils;
import ru.zaochno.zaochno.ui.drawer.DrawerItemCreator;

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
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_training,
                            "Мои тренинги",
                            1,
                            true,
                            TrainingListActivity.class,
                            true),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_favourite,
                            "Избранное",
                            2,
                            true,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(BaseNavDrawerActivity.this, TrainingListActivity.class)
                                            .putExtra(TrainingListActivity.INTENT_KEY_TAB, TrainingListActivity.INTENT_KEY_TAB_FAVOURITE));
                                    finish();
                                }
                            }),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_testing,
                            "Тестирование",
                            3,
                            true,
                            null,
                            true),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_email,
                            R.layout.drawer_item_messages,
                            "Мои сообщения",
                            4,
                            true,
                            MessageListActivity.class,
                            true),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_settings,
                            "Настройка",
                            5,
                            true,
                            AccountSettingsActivity.class,
                            true),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_exit,
                            "Выход",
                            6,
                            false,
                            new View.OnClickListener() {
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
                    DrawerItemCreator.createTextItem(this,
                            "Для полноценного пользования всему функциями приложения ZaOchno.Ru, пожалуйста, авторизируйтесь или зарегистрируйтесь",
                            true,
                            1),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_favourite,
                            "Авторизация",
                            2,
                            true,
                            LoginActivity.class,
                            true),
                    DrawerItemCreator.createItem(this,
                            R.drawable.ic_training,
                            "Тренинги",
                            3,
                            true,
                            TrainingListActivity.class,
                            true)
            );

        setupHeader(drawer.getHeader());

        if (AuthProvider.getInstance(this).isAuthenticated())
            setupFooter(drawer.getStickyFooter());

        drawer.setSelection(-1);

        if (AuthProvider.getInstance(this).isAuthenticated())
            checkUnreadMessages();
    }

    private void checkUnreadMessages() {
        Retrofit2Client.getInstance().getApi().getMessages(new TokenRequest(AuthProvider.getInstance(this).getCurrentUser().getToken()))
                .enqueue(new Callback<DataResponseWrapper<List<Message>>>() {
                    @Override
                    public void onResponse(Call<DataResponseWrapper<List<Message>>> call, Response<DataResponseWrapper<List<Message>>> response) {
                        Integer unreadCount = 0;

                        if (response.body().getResponseObj() != null) {
                            for (Message message : response.body().getResponseObj())
                                if (!message.getRead())
                                    unreadCount++;

                            ((ContainerDrawerItem) drawer.getDrawerItem(4)).getView().findViewById(R.id.tv_unread_count)
                                    .setVisibility(unreadCount == 0 ? View.GONE : View.VISIBLE);

                            if (unreadCount > 0)
                                ((TextView) ((ContainerDrawerItem) drawer.getDrawerItem(4)).getView().findViewById(R.id.tv_unread_count))
                                        .setText(String.valueOf(unreadCount));
                        }
                    }

                    @Override
                    public void onFailure(Call<DataResponseWrapper<List<Message>>> call, Throwable t) {

                    }
                });
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
