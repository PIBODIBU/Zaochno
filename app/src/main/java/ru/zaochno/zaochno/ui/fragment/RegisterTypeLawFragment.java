package ru.zaochno.zaochno.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.enums.UserType;
import ru.zaochno.zaochno.data.model.User;
import ru.zaochno.zaochno.databinding.FragmentRegisterTypeLawBinding;

public class RegisterTypeLawFragment extends BaseRegisterFragment {
    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    @BindView(R.id.checkbox_agree)
    public AppCompatCheckBox checkBoxAgree;

    private User user = new User();
    private FragmentRegisterTypeLawBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_type_law, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);

        user.setType(UserType.LAW.rawType());
        binding.setUser(user);

        Picasso.with(getActivity())
                .load(R.drawable.logo)
                .into(ivLogo);

        return view;
    }

    @OnClick(R.id.btn_register)
    public void register() {
        if (!checkBoxAgree.isChecked()) {
            Toast.makeText(getActivity(), R.string.you_must_agree, Toast.LENGTH_LONG).show();
            return;
        }

        if (this.registerActionListener != null)
            registerActionListener.onRegister(binding.getUser());
    }
}
