package ru.zaochno.zaochno.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixplicity.htmlcompat.HtmlCompat;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xml.sax.Attributes;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.ChapterSelectedEvent;
import ru.zaochno.zaochno.data.event.SubChapterSelectedEvent;
import ru.zaochno.zaochno.data.event.TrainingFullLoadedEvent;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.data.model.TrainingFull;
import ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity;
import ru.zaochno.zaochno.utils.PicassoTargetDrawable;
import ru.zaochno.zaochno.utils.UrlUtils;

import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.INTENT_KEY_CHAPTER_ID;
import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.INTENT_KEY_SHOW_TYPE;
import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.INTENT_KEY_SUB_CHAPTER_ID;
import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.INTENT_KEY_TRAINING_ID;
import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.SHOW_TYPE_CHAPTER;
import static ru.zaochno.zaochno.ui.activity.TrainingMaterialActivity.SHOW_TYPE_SUB_CHAPTER;

public class MaterialTextFragment extends Fragment {
    private static final String TAG = "MaterialTextFragment";

    @BindView(R.id.tv_text)
    public TextView tvText;

    public Spanned spanned;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_text, container, false);
        ButterKnife.bind(this, view);

        if (spanned != null)
            setMainText(spanned);

        return view;
    }

    @Subscribe
    public void onChapterSelectedEvent(ChapterSelectedEvent event) {
        if (event.getChapter() != null)
            setMainText(event.getChapter().getHtmlText());
    }

    @Subscribe
    public void onSubChapterSelectedEvent(SubChapterSelectedEvent event) {
        if (event.getSubChapter() != null)
            setMainText(event.getSubChapter().getHtmlText());
    }

    private void setMainText(Spanned spanned) {
        if (spanned != null) {
            tvText.setMovementMethod(LinkMovementMethod.getInstance());
            tvText.setText(spanned);
        }
    }

    private void setMainText(String htmlText) {
        if (htmlText != null) {
            tvText.setMovementMethod(LinkMovementMethod.getInstance());
            final Spanned spanned = HtmlCompat.fromHtml(getActivity(), htmlText, 0, new HtmlCompat.ImageGetter() {
                @Override
                public Drawable getDrawable(String source, Attributes attributes) {
                   /* PicassoTargetDrawable targetDrawable = new PicassoTargetDrawable(getActivity());

                    Picasso.with(getActivity())
                            .load(UrlUtils.htmlPathToUrl(source))
                            .into(targetDrawable);

                    return targetDrawable;*/
                    return new ColorDrawable(Color.TRANSPARENT);
                }
            });

            tvText.setText(spanned);
            this.spanned = spanned;
        }
    }
}
