package ru.zaochno.zaochno.ui.activity;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.event.TrainingFavouriteEvent;
import ru.zaochno.zaochno.data.model.BaseChapter;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.response.BaseErrorResponse;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.data.utils.DateUtils;
import ru.zaochno.zaochno.databinding.ActivityTrainingInfoBinding;
import ru.zaochno.zaochno.ui.adapter.ChapterListAdapter;

public class TrainingInfoActivity extends BaseNavDrawerActivity {
    private static final String TAG = "TrainingInfoActivity";

    public static final String INTENT_KEY_TRAINING_MODEL = "INTENT_KEY_TRAINING_MODEL";

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.btn_buy)
    public Button btnBuy;

    @BindView(R.id.btn_read_more)
    public Button btnReadMore;

    @BindView(R.id.tv_description)
    public TextView tvDescription;

    @BindView(R.id.container_progress)
    public View containerProgress;

    @BindView(R.id.tv_valid_tow)
    public TextView tvValidTo;

    @BindView(R.id.btn_to_fav)
    public ImageButton ibFavourite;

    @BindView(R.id.btn_demo)
    public ImageButton ibDemo;

    @BindView(R.id.btn_test)
    public ImageButton ibTest;

    @BindView(R.id.btn_share)
    public ImageButton ibShare;

    @BindView(R.id.btn_schedule)
    public Button btnSchedule;

    @BindView(R.id.switch_view)
    public Switch aSwitch;

    @BindView(R.id.recycler_view_title)
    public TextView tvRecyclerViewTitle;

    @BindView(R.id.recycler_view_chapters)
    public RecyclerView recyclerViewChapters;

    private Training training;
    private TrainingFull trainingFull;
    private ActivityTrainingInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_info);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        setupDrawer();

        if (!getDataFromIntent())
            return;
        fetchTraining();
    }

    private void fetchTraining() {
        if (AuthProvider.getInstance(this).isAuthenticated())
            training.setUserToken(AuthProvider.getInstance(this).getCurrentUser().getToken());

        Retrofit2Client.getInstance().getApi().getFullTraining(training).enqueue(new Callback<DataResponseWrapper<TrainingFull>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<TrainingFull>> call, Response<DataResponseWrapper<TrainingFull>> response) {
                if (response == null || response.body() == null) {
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

        aSwitch.setChecked(binding.getTraining().getPayed());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.getTraining().setPayed(b);
                setupUi();
            }
        });

        if (training.getFavourite())
            ibFavourite.setImageResource(R.drawable.ic_star_white_24dp);
        else
            ibFavourite.setImageResource(R.drawable.ic_star_border_white_24dp);

        if (training.getPayed())
            setupUiPayed();
        else
            setupUiNotPayed();
    }

    private void setupUiPayed() {
        btnBuy.setVisibility(View.GONE);
        tvRecyclerViewTitle.setVisibility(View.VISIBLE);
        containerProgress.setVisibility(View.VISIBLE);
        btnSchedule.setVisibility(View.VISIBLE);

        tvDescription.setMaxLines(3);

        btnReadMore.setVisibility(View.VISIBLE);
        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDescription.setMaxLines(999);
                btnReadMore.setVisibility(View.GONE);
            }
        });

        if (trainingFull.getDurationMillis() != null && trainingFull.getPurchaseDate() != null) {
            tvValidTo.setVisibility(View.VISIBLE);
            tvValidTo.setText(tvValidTo.getText().toString().concat(
                    DateUtils.millisToPattern(trainingFull.getDurationMillis() + trainingFull.getPurchaseDate(), DateUtils.PATTERN_DEFAULT)
            ));
        }

        setupRecyclerViews();
    }

    private void setupUiNotPayed() {
        btnBuy.setVisibility(View.VISIBLE);
        containerProgress.setVisibility(View.GONE);
        btnSchedule.setVisibility(View.GONE);

        btnBuy.setText("Купить (от ".concat(String.valueOf(training.getLowestPrice().getPrice())).concat(" руб.)"));

        tvDescription.setMaxLines(999);

        btnReadMore.setVisibility(View.GONE);
        tvValidTo.setVisibility(View.GONE);
        tvRecyclerViewTitle.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onTrainingFavouriteEvent(TrainingFavouriteEvent event) {
        training = event.getTraining();
        fetchTraining();
    }

    private void setupRecyclerViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinkedList<BaseChapter> chapters = new LinkedList<>();

        // Prepare data set
        for (int k = 0; k < trainingFull.getChapters().size(); k++) {
            Chapter chapter = trainingFull.getChapters().get(k);
            chapter.setPosition(k + 1);
            chapters.add(chapter);

            if (chapter.getSubChapters() != null)
                for (int i = 0; i < chapter.getSubChapters().size(); i++) {
                    if (chapter.getSubChapters().get(i) != null) {
                        chapter.getSubChapters().get(i).setPosition(i + 1);
                        chapters.add(chapter.getSubChapters().get(i));
                    }
                }
        }

        ChapterListAdapter adapter = new ChapterListAdapter(this, chapters);

        recyclerViewChapters.setAdapter(adapter);
        recyclerViewChapters.setHasFixedSize(true);
        recyclerViewChapters.setLayoutManager(layoutManager);
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

    @OnClick(R.id.btn_schedule)
    public void schedule() {
        startActivity(new Intent(TrainingInfoActivity.this, ExamNewActivity.class)
                .putExtra(ExamNewActivity.INTENT_KEY_TRAINING_MODEL, training));
    }

    @OnClick(R.id.btn_buy)
    public void onBuy() {
    }

    @OnClick(R.id.btn_demo)
    public void onDemo() {
    }

    @OnClick(R.id.btn_to_fav)
    public void onFavourite() {
        // Invert favourite status
        if (!AuthProvider.getInstance(this).isAuthenticated())
            return;

        training.setFavourite(!training.getFavourite());
        training.setUserToken(AuthProvider.getInstance(this).getCurrentUser().getToken());

        Retrofit2Client.getInstance().getApi().favouriteTraining(training).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onResponse(Call<BaseErrorResponse> call, Response<BaseErrorResponse> response) {
                if (response == null || response.body() == null || response.body().getError()) {
                    Toast.makeText(TrainingInfoActivity.this, R.string.error, Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(TrainingInfoActivity.this, R.string.added_to_fav, Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new TrainingFavouriteEvent(training));
            }

            @Override
            public void onFailure(Call<BaseErrorResponse> call, Throwable t) {
                Toast.makeText(TrainingInfoActivity.this, R.string.error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.btn_test)
    public void onTest() {
        startActivity(new Intent(TrainingInfoActivity.this, TestListActivity.class)
                .putExtra(TestListActivity.INTENT_KEY_TRAINING_ID, training.getId()));
        finish();
    }

    @OnClick(R.id.btn_share)
    public void onShare() {
    }
}