package com.asmat.rolando.bakingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.asmat.rolando.bakingapp.R
import com.asmat.rolando.bakingapp.models.Recipe
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.source.TrackGroupArray

class CompleteStepFragment : Fragment(), ExoPlayer.EventListener {
    private var mRecipe: Recipe? = null
    private var mPosition: Int? = null
    private var simpleExoPlayerView: SimpleExoPlayerView? = null
    private var mPlayer: SimpleExoPlayer? = null
    private var mMediaSession: MediaSessionCompat? = null
    private val TAG = "CompleteStepFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mRecipe = arguments.getParcelable(ARG_RECIPE)
            mPosition = arguments.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_complete_step, container, false)

        val descriptionTextView = view.findViewById(R.id.complete_step_description_text_view) as TextView
        descriptionTextView.movementMethod = ScrollingMovementMethod()
        val stepDescription = mRecipe!!.steps[mPosition!!].description
        descriptionTextView.text = stepDescription

        simpleExoPlayerView = view.findViewById(R.id.step_simple_exo_player_view) as SimpleExoPlayerView
        val uriString = mRecipe!!.steps[mPosition!!].videoURL
        if(uriString.equals("")) {
            simpleExoPlayerView?.visibility = View.GONE
            container?.removeView(simpleExoPlayerView)
        } else {
            val bandwidthMeter = DefaultBandwidthMeter()
            val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
            mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
            simpleExoPlayerView?.player = mPlayer!!
            val appName = resources.getString(R.string.app_name)
            val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, appName), bandwidthMeter)
            val extractorsFactory = DefaultExtractorsFactory()
            val uri = Uri.parse(uriString)
            val videoSource = ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
            mPlayer?.addListener(this)
            mPlayer?.prepare(videoSource)
            simpleExoPlayerView?.requestFocus()
            setupMediaSession()
        }
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            mMediaSession?.isActive = true
        } else {
            mPlayer?.playWhenReady = false
            mMediaSession?.isActive = false
        }
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.playWhenReady = false
        mMediaSession?.isActive = false
    }

    override fun onResume() {
        super.onResume()
        mMediaSession?.isActive = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer?.release()
        mMediaSession?.release()
    }

    fun setupMediaSession() {
        mMediaSession = MediaSessionCompat(context, TAG)

        val flags = MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        mMediaSession?.setFlags(flags)
        mMediaSession?.setMediaButtonReceiver(null)

        val actions = PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
        val initialStateBuilder = PlaybackStateCompat.Builder().setActions(actions)

        mMediaSession?.setPlaybackState(initialStateBuilder.build())
        mMediaSession?.setCallback(MySessionCallback())
        mMediaSession?.isActive = true
    }

    // ExoPlayer.EventListener overrides

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING ->
                Log.v(TAG, "STATE_BUFFERING")
            ExoPlayer.STATE_IDLE ->
                Log.v(TAG, "STATE_IDLE")
            ExoPlayer.STATE_READY -> {
                Log.v(TAG, "STATE_READY")
                var state: Int
                if(playWhenReady) {
                    Log.v(TAG, "We are playing")
                    state = PlaybackStateCompat.STATE_PLAYING
                } else {
                    Log.v(TAG, "We are paused")
                    state = PlaybackStateCompat.STATE_PAUSED
                }
                // Update session state
                val player = mPlayer
                if(player != null) {
                    val stateBuilder = PlaybackStateCompat.Builder().setState(state, player.currentPosition, 1f)
                    mMediaSession?.setPlaybackState(stateBuilder.build())
                }
            }
            ExoPlayer.STATE_ENDED ->
                Log.v(TAG, "STATE_ENDED")
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) { }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) { }

    override fun onPlayerError(error: ExoPlaybackException?) { }

    override fun onLoadingChanged(isLoading: Boolean) { }

    override fun onPositionDiscontinuity() { }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) { }

    // MediaSession Callbacks. This is how external clients can interact with the player.

    inner class MySessionCallback: MediaSessionCompat.Callback() {

        override fun onPlay() {
            super.onPlay()
            mPlayer?.playWhenReady = true
        }

        override fun onPause() {
            super.onPause()
            mPlayer?.playWhenReady = false
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            mPlayer?.seekTo(0)
        }
    }

    companion object {
        private val ARG_RECIPE = "arg_recipe"
        private val ARG_POSITION = "arg_position"

        fun newInstance(recipe: Recipe, position: Int): CompleteStepFragment {
            val fragment = CompleteStepFragment()
            val args = Bundle()
            args.putParcelable(ARG_RECIPE, recipe)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}