package ru.zaochno.zaochno.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixplicity.htmlcompat.HtmlCompat;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;
import ru.zaochno.zaochno.data.model.Chapter;
import ru.zaochno.zaochno.data.model.SubChapter;
import ru.zaochno.zaochno.ui.activity.VideoActivity;
import ru.zaochno.zaochno.ui.adapter.MaterialPhotoListAdapter;
import ru.zaochno.zaochno.ui.adapter.MaterialVideoListAdapter;
import ru.zaochno.zaochno.ui.dialog.VideoDialog;
import ru.zaochno.zaochno.utils.UrlUtils;

public class MaterialVideoFragment extends Fragment implements MaterialVideoListAdapter.OnImageClickListener {
    private static final String TAG = "MaterialVideoFragment";

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private Context context;
    private MaterialVideoListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_photo, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        return view;
    }

    public void setItem(Chapter chapter) {
        prepareHtml(chapter.getHtmlText());
    }

    public void setItem(SubChapter subChapter) {
        prepareHtml(subChapter.getHtmlText());
    }

    private void setupRecyclerView() {
        if (adapter == null)
            adapter = new MaterialVideoListAdapter(getActivity(), this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void prepareHtml(String html) {
        if (html == null)
            return;

        if (adapter == null)
            adapter = new MaterialVideoListAdapter(getActivity(), this);

        adapter.clearUrls();

        HtmlCompat.fromHtml(context, html, 0, null, new HtmlCompat.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Attributes attributes, Editable output, XMLReader xmlReader) {
                if (adapter != null && tag != null && attributes != null && tag.equals("video")) {
                    adapter.addUrl(UrlUtils.htmlPathToUrl(attributes.getValue("src")));
                }
            }
        });
    }

    @Override
    public void onVideoClick(String url) {
        startActivity(new Intent(getActivity(), VideoActivity.class)
                .putExtra(VideoActivity.INTENT_KEY_VIDEO_URL, url));

        /*VideoDialog videoDialog = new VideoDialog();
        videoDialog.setUrl(url);
        videoDialog.show(getActivity().getSupportFragmentManager(), "VideoDialog");*/
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
