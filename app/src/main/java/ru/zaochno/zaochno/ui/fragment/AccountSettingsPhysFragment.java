package ru.zaochno.zaochno.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.databinding.FragmentAccountSettingsPhysBinding;
import ru.zaochno.zaochno.ui.callback.OnUserUpdateListener;

import static android.app.Activity.RESULT_OK;

public class AccountSettingsPhysFragment extends Fragment {
    private static final int REQ_CODE_PICK_IMAGE = 9002;
    private static final String TAG = "SettingsPhysFragment";

    @BindView(R.id.civ_avatar)
    public CircleImageView civAvatar;

    @BindView(R.id.container_no_avatar)
    public View containerNoImage;

    @BindView(R.id.iv_head_background)
    public ImageView ivHeadBackground;

    private User user;
    private FragmentAccountSettingsPhysBinding binding;
    private OnUserUpdateListener onUserUpdateListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_settings_phys, container, false);
        binding.setUser(user);
        ButterKnife.bind(this, binding.getRoot());

        Picasso.with(getActivity())
                .load(R.drawable.account_header_bg)
                .into(ivHeadBackground);

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    //String selectedImagePath = FileUtils.getPathFromUri(this, selectedImageUri);
                    //Bitmap selectedImageBitmap = BitmapFactory.decodeFile(selectedImagePath);

                    Picasso.with(getActivity())
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

    @OnClick(R.id.btn_update)
    public void update() {
        if (onUserUpdateListener != null)
            onUserUpdateListener.onUserInfoUpdate(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOnUserUpdateListener(OnUserUpdateListener onUserUpdateListener) {
        this.onUserUpdateListener = onUserUpdateListener;
    }
}
