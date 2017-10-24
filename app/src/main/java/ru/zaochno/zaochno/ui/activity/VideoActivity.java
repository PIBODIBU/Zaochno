package ru.zaochno.zaochno.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class VideoActivity extends AppCompatActivity {
    public static final String INTENT_KEY_VIDEO_URL = "INTENT_KEY_VIDEO_URL";

    @BindView(R.id.video_view)
    public VideoView videoView;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTheme(R.style.Theme_AppCompat_Dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(INTENT_KEY_VIDEO_URL))
            url = getIntent().getExtras().getString(INTENT_KEY_VIDEO_URL);

        if (url == null)
            return;

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }
}
