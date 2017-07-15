package com.asmat.rolando.bakingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.source.TrackGroupArray

class CompleteStepFragment : Fragment(), ExoPlayer.EventListener {
    private var mRecipe: Recipe? = null
    private var mPosition: Int? = null
    var simpleExoPlayerView: SimpleExoPlayerView? = null
    var player: SimpleExoPlayer? = null

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
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
            simpleExoPlayerView?.player = player!!
            val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "BakingApp"), bandwidthMeter)
            val extractorsFactory = DefaultExtractorsFactory()
            val uri = Uri.parse(uriString)
            val videoSource = ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null)
            player?.addListener(this)
            player?.prepare(videoSource)
            simpleExoPlayerView?.requestFocus()
        }
        return view
    }

    override fun onPause() {
        super.onPause()
        if (player != null) {
            player?.playWhenReady = false //to pause a video because now our video player is not in focus
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()   //it is important to release a player
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING ->
                print("STATE_BUFFERING")
            ExoPlayer.STATE_IDLE ->
                print("STATE_IDLE")
            ExoPlayer.STATE_READY -> {
                if(playWhenReady) {
                    // We are playing
                } else {
                    // We are paused

                }
            }
            ExoPlayer.STATE_ENDED ->
                print("STATE_ENDED")
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        //
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPositionDiscontinuity() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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