package com.example.adpoc

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        pref = applicationContext.getSharedPreferences(Constants.USER_DETAILS, 0) // 0 - for private mode
        editor = pref?.edit()
        initializePlayer()
    }

    private fun createMediaSource(videoUrl: String): MediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        val dataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl))
    }

    private fun createMediaSourceWithAds(videoUrl: String, exoPlayerView: PlayerView): AdsMediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        //Creating  Ima Ads Loader
       /* val adUrl =  when(pref?.getString(Constants.SELECTED_AD_PREFERENCE,"")){
            Constants.SPORT -> VideoConfig.SPORTS_AD_URL
            Constants.FOOD -> VideoConfig.FOOD_AD_URL
            Constants.TRAVEL -> VideoConfig.TRAVEL_AD_URL
            Constants.ENTERTAINMENT -> VideoConfig.ENTERTAINMENT_AD_URL
            else -> ""
        }*/
        val imaAdsLoader = ImaAdsLoader(this, Uri.parse(VideoConfig.SPORTS_AD_URL))
        imaAdsLoader.addCallback(object : VideoAdPlayer.VideoAdPlayerCallback {
            override fun onVolumeChanged(p0: Int) {
            }

            override fun onResume() {
            }

            override fun onPause() {
            }

            override fun onError() {
            }

            override fun onEnded() {
                when(pref?.getString(Constants.SELECTED_AD_PREFERENCE,"")){
                    Constants.SPORT -> editor?.putInt(Constants.SPORT_AD_COUNT,(pref!!.getInt(Constants.SPORT_AD_COUNT,0) +1))?.apply()
                    Constants.FOOD -> editor?.putInt(Constants.FOOD_AD_COUNT,(pref!!.getInt(Constants.FOOD_AD_COUNT,0) +1))?.apply()
                    Constants.TRAVEL -> editor?.putInt(Constants.TRAVEL_AD_COUNT,(pref!!.getInt(Constants.TRAVEL_AD_COUNT,0) +1))?.apply()
                    Constants.ENTERTAINMENT -> editor?.putInt(Constants.ENTERTAINMENT_AD_COUNT,(pref!!.getInt(Constants.ENTERTAINMENT_AD_COUNT,0)+1))?.apply()
                }
            }

            override fun onPlay() {
            }
        })

        //Creating Video Content Media Source With Ads
        return AdsMediaSource(
            createMediaSource(videoUrl), //Video Content Media Source
            DefaultDataSourceFactory(this, userAgent),
            imaAdsLoader,
            exoPlayerView.overlayFrameLayout
        )
    }

    private fun initializePlayer() {
        val loadControl = DefaultLoadControl(
            DefaultAllocator(true, 16),
            VideoConfig.MIN_BUFFER_DURATION,
                VideoConfig.MAX_BUFFER_DURATION,
                VideoConfig.MIN_PLAYBACK_START_BUFFER,
                VideoConfig.MIN_PLAYBACK_RESUME_BUFFER,
            C.LENGTH_UNSET, false
        )

        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(this),
            DefaultTrackSelector(),
            loadControl
        )
        exoPlayerView.player = player
        //player?.prepare(createMediaSource(VideoPlayerConfig.VIDEO_URL))
        player?.prepare(createMediaSourceWithAds(VideoConfig.VIDEO_URL, exoPlayerView))
        player?.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
            }

            override fun onPositionDiscontinuity(reason: Int) {
            }

            override fun onSeekProcessed() {
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                    }
                    Player.STATE_READY -> {
                    }
                    Player.STATE_ENDED->{
                        if(playWhenReady){
                            player?.playWhenReady = false
                            player?.seekTo(0)
                            when(pref?.getString(Constants.SELECTED_AD_PREFERENCE,"")){
                                Constants.SPORT -> editor?.putInt(Constants.SPORT_VIDEO_COUNT,(pref!!.getInt(Constants.SPORT_VIDEO_COUNT,0)+1))
                                Constants.FOOD -> editor?.putInt(Constants.FOOD_VIDEO_COUNT,(pref!!.getInt(Constants.FOOD_VIDEO_COUNT,0)+1))
                                Constants.TRAVEL -> editor?.putInt(Constants.TRAVEL_VIDEO_COUNT,(pref!!.getInt(Constants.TRAVEL_VIDEO_COUNT,0)+1))
                                Constants.ENTERTAINMENT -> editor?.putInt(Constants.ENTERTAINMENT_VIDEO_COUNT,(pref!!.getInt(Constants.ENTERTAINMENT_VIDEO_COUNT,0)+1))
                            }
                            editor?.apply()
                        }
                    }
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {}

            override fun onPlayerError(error: ExoPlaybackException) {
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            }
        })

    }

    override fun onResume() {
        player?.playWhenReady = true
        super.onResume()
    }

    override fun onPause() {
        player?.playWhenReady = false
        super.onPause()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}
