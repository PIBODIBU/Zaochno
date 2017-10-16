package ru.zaochno.zaochno.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.api.Retrofit2Client;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.data.model.SubThematic;
import ru.zaochno.zaochno.data.model.Training;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.data.model.response.DataResponseWrapper;
import ru.zaochno.zaochno.data.provider.AuthProvider;

public class TrainingMaterialActivity extends BaseNavDrawerActivity {
    private static final String TAG = "TrainingMaterActivity";
    public static final String INTENT_KEY_TRAINING_ID = "INTENT_KEY_TRAINING_ID";
    public static final String INTENT_KEY_SHOW_TYPE = "INTENT_KEY_SHOW_TYPE";
    public static final String SHOW_TYPE_CHAPTER = "SHOW_TYPE_CHAPTER";
    public static final String SHOW_TYPE_SUB_CHAPTER = "SHOW_TYPE_SUB_CHAPTER";
    public static final String INTENT_KEY_CHAPTER_ID = "INTENT_KEY_CHAPTER_ID";
    public static final String INTENT_KEY_SUB_CHAPTER_ID = "INTENT_KEY_SUB_CHAPTER_ID";

    @BindView(R.id.coordinator)
    public CoordinatorLayout coordinator;

    @BindView(R.id.tv_text)
    public TextView tvText;

    @BindView(R.id.spinner_thematic)
    public AppCompatSpinner spinnerThematic;

    @BindView(R.id.spinner_sub_thematic)
    public AppCompatSpinner spinnerSubThematic;

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

        fetchTraining(getTrainingIdFromIntent());
    }

    private void fetchTraining(Integer id) {
        Training params = new Training(id);
        params.setUserToken(AuthProvider.getInstance(this).getCurrentUser().getToken());

        Retrofit2Client.getInstance().getApi().getFullTraining(params).enqueue(new Callback<DataResponseWrapper<TrainingFull>>() {
            @Override
            public void onResponse(Call<DataResponseWrapper<TrainingFull>> call, Response<DataResponseWrapper<TrainingFull>> response) {
                if (response == null || response.body() == null) {
                    showSnackBar(getString(R.string.error));
                    return;
                }

                setTrainingFull(response.body().getResponseObj());
                setupUi();
            }

            @Override
            public void onFailure(Call<DataResponseWrapper<TrainingFull>> call, Throwable t) {
                showSnackBar(getString(R.string.error));
            }
        });
    }

    private void setTrainingFull(TrainingFull trainingFull) {
        this.trainingFull = trainingFull;
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
        for (Chapter chapter : trainingFull.getChapters()) {
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

    private void setupUi() {
        Chapter chapter = null;
        SubChapter subChapter = null;

        if (getShowTypeFromIntent() == null)
            return;

        switch (getShowTypeFromIntent()) {
            case SHOW_TYPE_CHAPTER:
                if (getChapterIdFromIntent() != null && getChapterById(getChapterIdFromIntent()) != null) {
                    chapter = getChapterById(getChapterIdFromIntent());
                    setMainText(chapter.getHtmlText());
                }
                break;
            case SHOW_TYPE_SUB_CHAPTER:
                if (getChapterIdFromIntent() != null && getSubChapterIdFromIntent() != null
                        && getSubChapterById(getChapterIdFromIntent(), getSubChapterIdFromIntent()) != null) {
                    chapter = getChapterById(getChapterIdFromIntent());
                    subChapter = getSubChapterById(getChapterIdFromIntent(), getSubChapterIdFromIntent());
                    setMainText(subChapter.getHtmlText());
                }
                break;
            default:
                break;
        }

        setupSpinnerThematics(chapter);
        setupSpinnerSubThematics(chapter, subChapter);
    }

    private void setupSpinnerThematics(Chapter selectedChapter) {
        final ArrayAdapter<Chapter> arrayAdapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.addAll(trainingFull.getChapters());
        spinnerThematic.setAdapter(arrayAdapter);

        if (selectedChapter != null)
            spinnerThematic.setSelection(getThematicAdapterPositionById(arrayAdapter, selectedChapter.getId()), false);

        spinnerThematic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private Boolean firstAdding = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter = arrayAdapter.getItem(position);
                refreshUi(chapter);

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

    private void setupSpinnerSubThematics(final Chapter chapter, final SubChapter subChapter) {
        if (chapter == null)
            return;

        final ArrayAdapter<SubChapter> adapter = new ArrayAdapter<>(getSupportActionBar().getThemedContext(), R.layout.support_simple_spinner_dropdown_item);
        adapter.addAll(chapter.getSubChapters());

        spinnerSubThematic.setAdapter(adapter);
        spinnerSubThematic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshUi(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (subChapter != null)
            spinnerSubThematic.setSelection(getSubThematicAdapterPositionById(adapter, subChapter.getId()));

        Log.d(TAG, "setupSpinnerSubThematics: " + (subChapter == null ? "null" : "not null"));
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

    private void refreshUi(Chapter chapter) {
        if (chapter != null)
            setMainText(chapter.getHtmlText());
    }

    private void refreshUi(SubChapter subChapter) {
        if (subChapter != null)
            setMainText(subChapter.getHtmlText());
    }

    private void refreshUi(Chapter chapter, SubChapter subChapter) {
        if (chapter != null && subChapter != null) {
            setMainText(chapter.getHtmlText().concat(subChapter.getHtmlText()));
        }
    }

    private void setMainText(String htmlText) {
        if (htmlText != null)
            tvText.setText(new SpannableString(Html.fromHtml(htmlText)));
    }

    private void showSnackBar(String text) {
        Snackbar.make(coordinator, text, Snackbar.LENGTH_LONG).show();
    }
}