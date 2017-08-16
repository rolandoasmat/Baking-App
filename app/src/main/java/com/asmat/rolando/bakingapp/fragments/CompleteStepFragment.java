package com.asmat.rolando.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asmat.rolando.bakingapp.R;
import com.asmat.rolando.bakingapp.models.Recipe;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class CompleteStepFragment extends Fragment implements ExoPlayer.EventListener {
    private Recipe mRecipe;
    private Integer mPosition;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer mPlayer;
    private MediaSessionCompat mMediaSession;
    private final String TAG = "CompleteStepFragment";
    private static final String ARG_RECIPE = "arg_recipe";
    private static final String ARG_POSITION = "arg_position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getArguments() != null) {
            this.mRecipe = getArguments().getParcelable(ARG_RECIPE);
            this.mPosition = Integer.valueOf(getArguments().getInt(ARG_POSITION));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_step, container, false);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.complete_step_description_text_view);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        String stepDescription = mRecipe.getSteps()[mPosition].getDescription();
        descriptionTextView.setText(stepDescription);
        simpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.step_simple_exo_player_view);
        String uriString = mRecipe.getSteps()[mPosition].getVideoURL();


        if(uriString.equals("")) {
            simpleExoPlayerView.setVisibility(View.GONE);
            container.removeView(simpleExoPlayerView);
        } else {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            simpleExoPlayerView.setPlayer(mPlayer);
            String appName = getResources().getString(R.string.app_name);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), appName), (TransferListener)bandwidthMeter);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            Uri uri = Uri.parse(uriString);
            ExtractorMediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            mPlayer.addListener(this);
            mPlayer.prepare(videoSource);
            simpleExoPlayerView.requestFocus();
            setupMediaSession();
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if(mMediaSession != null) {
                mMediaSession.setActive(true);
            }
        } else {
            if(mPlayer != null){
                mPlayer.setPlayWhenReady(false);
            }
            if(mMediaSession != null) {
                mMediaSession.setActive(false);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer != null) {
            mPlayer.setPlayWhenReady(false);
        }
        if(mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMediaSession != null) {
            mMediaSession.setActive(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer != null) {
            mPlayer.release();
        }
        if(mMediaSession != null) {
            mMediaSession.release();
        }
    }

    public final void setupMediaSession() {
        this.mMediaSession = new MediaSessionCompat(this.getContext(), this.TAG);
        int flags = MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS + MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS;
        mMediaSession.setFlags(flags);
        mMediaSession.setMediaButtonReceiver(null);
        long actions = PlaybackStateCompat.ACTION_PLAY + PlaybackStateCompat.ACTION_PAUSE +
        PlaybackStateCompat.ACTION_PLAY_PAUSE + PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
        PlaybackStateCompat.Builder initialStateBuilder = (new PlaybackStateCompat.Builder()).setActions(actions);
        mMediaSession.setPlaybackState(initialStateBuilder.build());
        mMediaSession.setCallback((new CompleteStepFragment.MySessionCallback()));
        mMediaSession.setActive(true);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch(playbackState) {
            case 1:
                Log.v(this.TAG, "STATE_IDLE");
                break;
            case 2:
                Log.v(this.TAG, "STATE_BUFFERING");
                break;
            case 3:
                Log.v(this.TAG, "STATE_READY");
                byte state;
                if(playWhenReady) {
                    Log.v(this.TAG, "We are playing");
                    state = 3;
                } else {
                    Log.v(this.TAG, "We are paused");
                    state = 2;
                }

                SimpleExoPlayer player = this.mPlayer;
                if(player != null) {
                    PlaybackStateCompat.Builder stateBuilder = (new PlaybackStateCompat.Builder()).setState(state, player.getCurrentPosition(), 1.0F);
                    mMediaSession.setPlaybackState(stateBuilder.build());
                }
                break;
            case 4:
                Log.v(this.TAG, "STATE_ENDED");
        }

    }

    public void onPlaybackParametersChanged( PlaybackParameters playbackParameters) {
    }

    public void onTracksChanged( TrackGroupArray trackGroups,  TrackSelectionArray trackSelections) {
    }

    public void onPlayerError( ExoPlaybackException error) {
    }

    public void onLoadingChanged(boolean isLoading) {
    }

    public void onPositionDiscontinuity() {
    }

    public void onTimelineChanged( Timeline timeline,  Object manifest) {
    }

    public final class MySessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            if(CompleteStepFragment.this.mPlayer != null) {
                CompleteStepFragment.this.mPlayer.setPlayWhenReady(true);
            }

        }

        @Override
        public void onPause() {
            super.onPause();
            if(CompleteStepFragment.this.mPlayer != null) {
                CompleteStepFragment.this.mPlayer.setPlayWhenReady(false);
            }
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            CompleteStepFragment.this.mPlayer.seekTo(0L);
        }
    }

    public static final CompleteStepFragment newInstance(Recipe recipe, int position) {
        CompleteStepFragment fragment = new CompleteStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
}
