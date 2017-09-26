package ru.zaochno.zaochno.ui.fragment;

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
import ru.zaochno.zaochno.R;

public class RegisterTypeLawFragment extends Fragment {
    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_type_law, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(getActivity())
                .load(R.drawable.logo)
                .into(ivLogo);

        return view;
    }
}
