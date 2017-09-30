package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.utils.FileUtils;

public class AccountSettingsActivity extends BaseNavDrawerActivity {
    private static final int REQ_CODE_PICK_IMAGE = 9002;
    private static final String TAG = "AccountSettingsActivity";

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    @BindView(R.id.iv_head_background)
    public ImageView ivHeadBackground;

    @BindView(R.id.civ_avatar)
    public CircleImageView civAvatar;

    @BindView(R.id.container_no_avatar)
    public View containerNoImage;

    public User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("");
        setupDrawer();

        setupUi();
        fetchData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    //String selectedImagePath = FileUtils.getPathFromUri(this, selectedImageUri);

                    //Bitmap selectedImageBitmap = BitmapFactory.decodeFile(selectedImagePath);

                    Picasso.with(this)
                            .load(selectedImageUri)
                            .into(civAvatar);

                    containerNoImage.setVisibility(View.GONE);
                }
        }
    }

    @OnClick(R.id.civ_avatar)
    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select avatar image"), REQ_CODE_PICK_IMAGE);
    }

    private void fetchData() {
        currentUser = AuthProvider.getInstance(this).getCurrentUser();
    }

    private void setupUi() {
        Picasso.with(this)
                .load(R.drawable.logo)
                .into(ivToolbarLogo);

        Picasso.with(this)
                .load(R.drawable.account_header_bg)
                .into(ivHeadBackground);
    }
}
