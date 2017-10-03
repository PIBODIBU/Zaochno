package ru.zaochno.zaochno.ui.activity;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.databinding.ActivityTrainingInfoBinding;

public class TrainingInfoActivity extends BaseNavDrawerActivity {
    private static final String TAG = "TrainingInfoActivity";

    public static final String INTENT_KEY_TRAINING_MODEL = "INTENT_KEY_TRAINING_MODEL";

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.iv_toolbar_logo)
    public ImageView ivToolbarLogo;

    @BindView(R.id.btn_buy)
    public Button btnBuy;

    @BindView(R.id.btn_read_more)
    public Button btnReadMore;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    @BindView(R.id.switch_view)
    public Switch aSwitch;

    private Training training;
    private TrainingFull trainingFull;
    private ActivityTrainingInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_info);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("");
        setupDrawer();

        if (!getDataFromIntent())
            return;
        fetchTraining();
    }

    private void fetchTraining() {
        Retrofit2Client.getInstance().getApi().getFullTraining(training).enqueue(new Callback<DataResponseWrapper<TrainingFull>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<TrainingFull>> call, Response<DataResponseWrapper<TrainingFull>> response) {
                if (response == null || response.body() == null || response.body().getResponseObj() == null) {
                    Toast.makeText(TrainingInfoActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
                    return;
                }

                trainingFull = response.body().getResponseObj();
                setupUi();
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<TrainingFull>> call, Throwable t) {
                Toast.makeText(TrainingInfoActivity.this, "Ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupUi() {
        binding.setTraining(training);
        binding.setTrainingFull(trainingFull);

        Picasso.with(this)
                .load(R.drawable.ic_launcher_logo)
                .into(ivToolbarLogo);

        aSwitch.setChecked(binding.getTraining().getPayed());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.getTraining().setPayed(b);
                setupUi();
            }
        });

        if (training.getPayed())
            setupUiPayed();
        else
            setupUiNotPayed();
    }

    private void setupUiPayed() {
        btnBuy.setVisibility(View.GONE);

        tvDescription.setMaxLines(4);

        btnReadMore.setVisibility(View.VISIBLE);
        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDescription.setMaxLines(999);
                btnReadMore.setVisibility(View.GONE);
            }
        });
    }

    private void setupUiNotPayed() {
        btnBuy.setVisibility(View.VISIBLE);
        btnBuy.setText("Купить (от ".concat(String.valueOf(training.getLowestPrice().getPrice())).concat(" руб.)"));

        tvDescription.setMaxLines(999);

        btnReadMore.setVisibility(View.GONE);
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .into(view);
    }

    public boolean getDataFromIntent() {
        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(INTENT_KEY_TRAINING_MODEL))
            return false;

        training = ((Training) getIntent().getExtras().get(INTENT_KEY_TRAINING_MODEL));
        return training != null;
    }
}
