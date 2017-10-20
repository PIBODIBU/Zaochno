package ru.zaochno.zaochno.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MaterialVideoFragment extends Fragment {
    public static MaterialVideoFragment newInstance() {

        Bundle args = new Bundle();

        MaterialVideoFragment fragment = new MaterialVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
