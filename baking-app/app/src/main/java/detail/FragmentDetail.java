package detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeViewModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import utils.Recipe;

public class FragmentDetail extends Fragment {
    private View mView;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private final String indexKey = "INDEX";
    private final String stepIndexKey = "STEP_INDEX";
    private String VIDEO_URL;
    private String THUMBNAIL_URL;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private int position = 0;
    private int stepPosition = 0;
    private String PLAY_WHEN_READY = "playWhenReady";
    private String CURRENT_WINDOW = "currentWindow";
    private String PLAYBACK_POSITION = "playbackPosition";
    private MediaSource mediaSource;
    private TextView instructions, instructionsTitle;
    private float scale;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null)
            mView = inflater.inflate(R.layout.fragment_detail, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerView = getView().findViewById(R.id.detail_player);
        instructionsTitle = getView().findViewById(R.id.steps_label);
        instructions = getView().findViewById(R.id.steps_instruction);
        initializePlayer();
        scale = getContext().getResources().getDisplayMetrics().density;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
        && !getResources().getBoolean(R.bool.isTablet)){
            goFullScreen();
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                && !getResources().getBoolean(R.bool.isTablet)){
            goSmallScreen();
        }
        else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && getResources().getBoolean(R.bool.isTablet)){
            tabletLandscape();
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && !getResources().getBoolean(R.bool.isTablet)){
            tabletPortrait();
        }
    }

    private void initializePlayer(){
        if(player == null) {
            Intent intent = getActivity().getIntent();
            if (intent.hasExtra(indexKey)) {
                position = intent.getIntExtra(indexKey, 0);
            }
            if (intent.hasExtra(stepIndexKey)) {
                stepPosition = intent.getIntExtra(stepIndexKey, 0);
            }
            Bundle bundle = getArguments();
            if(bundle != null){
                position = bundle.getInt(indexKey);
                stepPosition = bundle.getInt(stepIndexKey);
            }
            Recipe recipe = RecipeViewModel.getRecipes().getValue().get(position);
            VIDEO_URL = recipe.getSteps().get(stepPosition).getVideoURL();
            THUMBNAIL_URL = recipe.getSteps().get(stepPosition).getThumbnailURL();
            if(THUMBNAIL_URL.equals("") && VIDEO_URL.equals("") ){
                playerView.setVisibility(View.GONE);
            } else {
                if(VIDEO_URL.equals(""))
                    VIDEO_URL = THUMBNAIL_URL;
                playerView.setVisibility(View.VISIBLE);
                player = ExoPlayerFactory.newSimpleInstance(getContext());
                playerView.setPlayer(player);
                Uri uri = Uri.parse(VIDEO_URL);
                mediaSource = buildMediaSource(uri);
                player.setPlayWhenReady(playWhenReady);
                player.seekTo(currentWindow, playbackPosition);
                player.prepare(mediaSource, false, false);
            }
            instructionsTitle.setText(recipe.getSteps().get(stepPosition).getShortDescription());
            instructions.setText(recipe.getSteps().get(stepPosition).getDescription());
        } else {
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare(mediaSource, false, false);
        }
    }


//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            hideSystemUi();
//        } else {
//            showSystemUi();
//        }
//    }

//    @SuppressLint("InlinedApi")
    private void goFullScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        instructionsTitle.setVisibility(View.GONE);
        instructions.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
    }

//    @SuppressLint("InlinedApi")
    private void goSmallScreen() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        instructionsTitle.setVisibility(View.VISIBLE);
        instructions.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.height = (int)(360 * scale + 0.5f);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private void tabletLandscape(){
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.height = (int)(480 * scale + 0.5f);
        params.width = (int)(720 * scale + 0.5f);
    }

    private void tabletPortrait(){
        ViewGroup.LayoutParams params = playerView.getLayoutParams();
        params.height = (int)(360 * scale + 0.5f);
        params.width = (int)(480 * scale + 0.5f);
    }

    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "exoPlayer");
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAY_WHEN_READY, player.getPlayWhenReady());
        outState.putInt(CURRENT_WINDOW, player.getCurrentWindowIndex());
        outState.putLong(PLAYBACK_POSITION, player.getCurrentPosition());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            Log.e("API2", "working");
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            initializePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
//            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }
}
