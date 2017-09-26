package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.ui.fragment.RegisterTypeLawFragment;
import ru.zaochno.zaochno.ui.fragment.RegisterTypePhysFragment;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.btn_type_phys)
    public Button btnTypePhys;

    @BindView(R.id.btn_type_law)
    public Button btnTypeLaw;

    private RegisterTypePhysFragment physFragment = new RegisterTypePhysFragment();
    private RegisterTypeLawFragment lawFragment = new RegisterTypeLawFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setupUi();
    }

    @OnClick({R.id.btn_type_phys, R.id.btn_type_law})
    public void selectType(Button button) {
        switch (button.getId()) {
            case R.id.btn_type_phys:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, physFragment)
                        .commit();

                btnTypePhys.setBackgroundResource(R.color.colorButtonPrimary);
                btnTypeLaw.setBackgroundResource(R.color.colorButtonPrimaryLight);
                break;

            case R.id.btn_type_law:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, lawFragment)
                        .commit();

                btnTypeLaw.setBackgroundResource(R.color.colorButtonPrimary);
                btnTypePhys.setBackgroundResource(R.color.colorButtonPrimaryLight);
                break;
        }
    }

    private void setupUi() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, physFragment)
                .commit();
    }
}
