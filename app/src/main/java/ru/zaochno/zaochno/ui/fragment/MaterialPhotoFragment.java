package ru.zaochno.zaochno.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.htmlcompat.HtmlCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.event.ChapterSelectedEvent;
import ru.zaochno.zaochno.data.event.SubChapterSelectedEvent;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.ui.adapter.MaterialPhotoListAdapter;
import ru.zaochno.zaochno.ui.dialog.PhotoDialog;
import ru.zaochno.zaochno.utils.UrlUtils;

public class MaterialPhotoFragment extends Fragment implements MaterialPhotoListAdapter.OnImageClickListener {
    private static final String TAG = "MaterialPhotoFragment";

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private Context context;
    private MaterialPhotoListAdapter adapter;
    private LinkedList<String> urls = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_photo, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        return view;
    }

    public void setItem(Chapter chapter) {
        if (chapter != null)
            setPhotos(chapter.getHtmlText());
    }

    public void setItem(SubChapter subChapter) {
        if (subChapter != null)
            setPhotos(subChapter.getHtmlText());
    }

    public void setPhotos(String htmlText) {
        if (htmlText == null)
            return;

        Spanned spanned = HtmlCompat.fromHtml(context, htmlText, 0);
        LinkedList<String> imageUrls = new LinkedList<>();
        for (ImageSpan imageSpan : spanned.getSpans(0, spanned.length(), ImageSpan.class))
            imageUrls.add(UrlUtils.htmlPathToUrl(imageSpan.getSource()));

        this.urls = imageUrls;

        if (adapter != null)
            adapter.setUrls(this.urls);
    }

    private void setupRecyclerView() {
        adapter = new MaterialPhotoListAdapter(urls, getActivity(), this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onImageClick(String url) {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setUrl(url);
        dialog.show(getActivity().getSupportFragmentManager(), "PhotoDialog");
    }
}