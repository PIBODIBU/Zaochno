package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.rey.material.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class DebugActivityLogin extends AppCompatActivity {
    @BindView(R.id.ib_menu)
    public ImageButton ibMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_debug_login);
        ButterKnife.bind(this);

        setupUi();
    }

    private void setupUi() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DebugActivityLogin.this, ibMenu);
                popupMenu.inflate(R.menu.menu_debug);
                popupMenu.show();
            }
        });
    }
}
