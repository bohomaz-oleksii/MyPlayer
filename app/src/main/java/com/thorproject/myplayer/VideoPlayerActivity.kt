package com.thorproject.myplayer

import android.app.Activity

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.thorproject.myplayer.databinding.ActivityVideoPlayerBinding
import com.thorproject.myplayer.db.VideoItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers



class VideoPlayerActivity : Activity() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var binding: ActivityVideoPlayerBinding

    private var mRepository: Repository? = null
    private var position: Int = 0
    private var videos: List<VideoItem>? = null
    var STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        position = intent.getIntExtra("pos", 0)

        mRepository = Repository(this)
        mRepository?.getRxAll()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ verse ->
                    videos = verse
                    Log.d("TAG", videos?.size.toString())
                    Log.d("Tag1", STREAM_URL)
                    initializePlayer()
                }, { error ->
                    finish()
                })


    }


    private fun initializePlayer() {

        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "myPlayer"))

        val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = SimpleExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.setMediaSources(setList(), position, 0)

        simpleExoPlayer.playWhenReady = true


        binding.playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        binding.playerView.player = simpleExoPlayer
        binding.playerView.requestFocus()
    }

    private fun setList() : List<MediaSource>{
        var mediaSource = mutableListOf<MediaSource>()
        if(videos != null) {
            for (i in videos!!.indices) {
                mediaSource.add(ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(MediaItem.fromUri(videos!![i].videoUrl)))
                //Log.d("TAG", videos!![i].videoUrl)
            }
        }
        Log.d("TAG3", videos?.size.toString())
        return mediaSource
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()

    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

    companion object {
//        const val STREAM_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
       const val STREAM_URL1 = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"
//        const val STREAM_URL2 = "https://html5demos.com/assets/dizzy.mp4"
    }
}