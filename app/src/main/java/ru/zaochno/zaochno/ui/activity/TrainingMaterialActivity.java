package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.event.ChapterSelectedEvent;
import ru.zaochno.zaochno.data.event.SubChapterSelectedEvent;
import ru.zaochno.zaochno.data.event.TrainingFullLoadedEvent;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;
import ru.zaochno.zaochno.ui.fragment.MaterialPhotoFragment;
import ru.zaochno.zaochno.ui.fragment.MaterialTextFragment;
import ru.zaochno.zaochno.ui.fragment.MaterialVideoFragment;

public class TrainingMaterialActivity extends BaseNavDrawerActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "TrainingMaterActivity";
    public static final String INTENT_KEY_TRAINING_ID = "INTENT_KEY_TRAINING_ID";
    public static final String INTENT_KEY_SHOW_TYPE = "INTENT_KEY_SHOW_TYPE";
    public static final String SHOW_TYPE_CHAPTER = "SHOW_TYPE_CHAPTER";
    public static final String SHOW_TYPE_SUB_CHAPTER = "SHOW_TYPE_SUB_CHAPTER";
    public static final String INTENT_KEY_CHAPTER_ID = "INTENT_KEY_CHAPTER_ID";
    public static final String INTENT_KEY_SUB_CHAPTER_ID = "INTENT_KEY_SUB_CHAPTER_ID";

    @BindView(R.id.coordinator)
    public CoordinatorLayout coordinator;

    @BindView(R.id.spinner_thematic)
    public AppCompatSpinner spinnerThematic;

    @BindView(R.id.spinner_sub_thematic)
    public AppCompatSpinner spinnerSubThematic;

    @BindView(R.id.bottom_navigation)
    public BottomNavigationView bottomNavigation;

    @BindView(R.id.container_loader)
    public View loader;

    public MaterialTextFragment textFragment = new MaterialTextFragment();
    public MaterialPhotoFragment photoFragment = new MaterialPhotoFragment();
    public MaterialVideoFragment videoFragment = new MaterialVideoFragment();

    private TrainingFull trainingFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_material);
        ButterKnife.bind(this);

        setupDrawer();
        setTitle("Материалы");

        if (!checkIntent()) {
            finish();
            return;
        }

        showLoader();
        photoFragment.setContext(this);
        fetchTraining(getTrainingIdFromIntent());
    }

    @Subscribe
    public void onTrainingFullLoaded(TrainingFullLoadedEvent event) {
        setTrainingFull(event.getTrainingFull());
        setupUi();
    }

    private void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    private void setupUi() {
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.action_text);

        Chapter chapter = null;
        SubChapter subChapter = null;

        if (getShowTypeFromIntent() == null)
            return;

        switch (getShowTypeFromIntent()) {
            case SHOW_TYPE_CHAPTER:
                if (getChapterIdFromIntent() != null && getChapterById(getChapterIdFromIntent()) != null) {
                    chapter = getChapterById(getChapterIdFromIntent());
                    EventBus.getDefault().post(new ChapterSelectedEvent(chapter));
                }
                break;
            case SHOW_TYPE_SUB_CHAPTER:
                if (getChapterIdFromIntent() != null && getSubChapterIdFromIntent() != null
                        && getSubChapterById(getChapterIdFromIntent(), getSubChapterIdFromIntent()) != null) {
                    chapter = getChapterById(getChapterIdFromIntent());
                    subChapter = getSubChapterById(getChapterIdFromIntent(), getSubChapterIdFromIntent());
                    EventBus.getDefault().post(new SubChapterSelectedEvent(subChapter));
                }
                break;
            default:
                break;
        }

        setupSpinnerThematics(chapter);
        setupSpinnerSubThematics(chapter, subChapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_text:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, textFragment)
                        .commit();
                return true;
            case R.id.action_photo:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, photoFragment)
                        .commit();
                return true;
            case R.id.action_video:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, videoFragment)
                        .commit();
                return true;

            default:
                return false;
        }
    }

    @Nullable
    private Integer getChapterIdFromIntent() {
        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(INTENT_KEY_CHAPTER_ID))
            return null;
        else
            return getIntent().getExtras().getInt(INTENT_KEY_CHAPTER_ID);
    }

    @Nullable
    private Integer getSubChapterIdFromIntent() {
        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(INTENT_KEY_SUB_CHAPTER_ID))
            return null;
        else
            return getIntent().getExtras().getInt(INTENT_KEY_SUB_CHAPTER_ID);
    }

    @Nullable
    private String getShowTypeFromIntent() {
        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(INTENT_KEY_SHOW_TYPE))
            return null;
        else
            return getIntent().getExtras().getString(INTENT_KEY_SHOW_TYPE);
    }

    @Nullable
    private Chapter getChapterById(Integer id) {
        for (Chapter chapter : getTrainingFull().getChapters()) {
            if (chapter.getId() == id)
                return chapter;
        }

        return null;
    }

    @Nullable
    private SubChapter getSubChapterById(Integer id, Integer subId) {
        Chapter chapter = getChapterById(id);

        if (chapter == null)
            return null;

        for (SubChapter subChapter : chapter.getSubChapters()) {
            if (subChapter.getId() == subId)
                return subChapter;
        }

        return null;
    }

    @NonNull
    private Integer getSubThematicAdapterPositionById(ArrayAdapter<SubChapter> adapter, Integer id) {
        Integer position = -1;

        for (int i = 0; i < adapter.getCount(); i++)
            if (adapter.getItem(i) != null && adapter.getItem(i).getId() == id) {
                position = i;
                break;
            }

        return position;
    }

    @NonNull
    private Integer getThematicAdapterPositionById(ArrayAdapter<Chapter> adapter, Integer id) {
        Integer position = -1;

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i) != null && adapter.getItem(i).getId() == id) {
                position = i;
                break;
            }
        }

        return position;
    }

    private void setupSpinnerThematics(Chapter selectedChapter) {
        final ArrayAdapter<Chapter> arrayAdapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.addAll(trainingFull.getChapters());
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerThematic.setAdapter(arrayAdapter);

        if (selectedChapter != null)
            spinnerThematic.setSelection(getThematicAdapterPositionById(arrayAdapter, selectedChapter.getId()), false);

        spinnerThematic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private Boolean firstAdding = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter = arrayAdapter.getItem(position);
                EventBus.getDefault().post(new ChapterSelectedEvent(chapter));
                photoFragment.setItem(chapter);

                if (firstAdding)
                    firstAdding = false;
                else
                    setupSpinnerSubThematics(chapter, null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSpinnerSubThematics(final Chapter chapter, SubChapter subChapter) {
        if (chapter == null)
            return;

        final ArrayAdapter<SubChapter> adapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), R.layout.support_simple_spinner_dropdown_item);
        adapter.addAll(chapter.getSubChapters());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinnerSubThematic.setAdapter(adapter);
        spinnerSubThematic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubChapter subChapter = adapter.getItem(position);

                EventBus.getDefault().post(new SubChapterSelectedEvent(subChapter));
                photoFragment.setItem(subChapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (subChapter != null)
            spinnerSubThematic.setSelection(getSubThematicAdapterPositionById(adapter, subChapter.getId()));
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

    private boolean checkIntent() {
        if (getIntent() == null || getIntent().getExtras() == null || !getIntent().getExtras().containsKey(INTENT_KEY_TRAINING_ID))
            return false;
        else
            return true;
    }

    @NonNull
    private Integer getTrainingIdFromIntent() {
        Integer id;

        try {
            id = getIntent().getExtras().getInt(INTENT_KEY_TRAINING_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
            id = -1;
        }

        return id;
    }

    private void fetchTraining(Integer id) {
        Training params = new Training(id);
        params.setUserToken(AuthProvider.getInstance(this).getCurrentUser().getToken());

        Retrofit2Client.getInstance().getApi().getFullTraining(params).enqueue(new Callback<DataResponseWrapper<TrainingFull>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<TrainingFull>> call, Response<DataResponseWrapper<TrainingFull>> response) {
                if (response == null || response.body() == null) {
                    return;
                }

                EventBus.getDefault().post(new TrainingFullLoadedEvent(response.body().getResponseObj()));
                hideLoader();
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<TrainingFull>> call, Throwable t) {
            }
        });
    }

    public TrainingFull getTrainingFull() {
        return trainingFull;
    }

    public void setTrainingFull(TrainingFull trainingFull) {
        this.trainingFull = trainingFull;
    }
}