package ru.zaochno.zaochno.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rey.material.widget.ProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.zaochno.zaochno.R;

public class VideoActivity extends AppCompatActivity {
    public static final String INTENT_KEY_VIDEO_URL = "INTENT_KEY_VIDEO_URL";

    @BindView(R.id.video_view)
    public VideoView videoView;

    @BindView(R.id.progress_bar)
    public ProgressView progressView;

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

        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                    progressView.setVisibility(View.GONE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                    progressView.setVisibility(View.VISIBLE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                    progressView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressView.setVisibility(View.GONE);
            }
        });
    }
}
