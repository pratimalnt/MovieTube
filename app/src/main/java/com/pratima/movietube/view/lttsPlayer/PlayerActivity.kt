package com.pratima.movietube.view.lttsPlayer

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.ltts.lttsplayer.LTTSPlayerView
import com.ltts.lttsplayer.configuration.PlayerConfig
import com.ltts.lttsplayer.events.Error
import com.ltts.lttsplayer.events.listeners.MediaControllerEvents
import com.ltts.lttsplayer.events.listeners.VideoPlayerEvents
import com.ltts.lttsplayer.playlists.PlaylistItem
import com.ltts.lttsplayer.ui.MediaPlayerControl
import com.pratima.movietube.R
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity(), MediaControllerEvents, VideoPlayerEvents.OnPlayerSetupListener, VideoPlayerEvents.OnPlayerEventListener{
    private lateinit var playLists: ArrayList<PlaylistItem>
    private lateinit var mediaPlayerControl: MediaPlayerControl
    private var isFullScreen = false
    private var videoPlayer: LTTSPlayerView? = null

    companion object{
        val TAG: String = PlayerActivity::class.java.simpleName
        const val videoUrl: String = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initialization()
        initVideoPlayer(playLists)
    }

    private fun initialization() {
        playLists= ArrayList()
        mediaPlayerControl = MediaPlayerControl(this)
        val playListItem = PlaylistItem.Builder()
            .setFile(videoUrl)
            .build()
        playLists.add(playListItem)

        mediaPlayerControl.setFullscreenButtonListener {
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun initVideoPlayer(playLists: ArrayList<PlaylistItem>) {
        val playerConfig = PlayerConfig.Builder()
            .setPlaylistItems(playLists)
            .setAutoStart(true)
            .build()

        playerContainer.addSetupListener(this)
        playerContainer.addMediaControlEventListener(this)
        playerContainer.setMediaPlayerControl(mediaPlayerControl)
        playerContainer.setup(playerConfig)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            playerContainer?.layoutParams = params
            isFullScreen = true
        }else{
            val params = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800)
            playerContainer?.layoutParams = params
            isFullScreen = false
        }
    }

    override fun onMediaControlsClick(view: View?) {
        if(view?.id == R.id.exo_fullscreen){
            requestedOrientation = if(!isFullScreen)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPlayerReady(videoPlayer: LTTSPlayerView?) {
        this.videoPlayer = videoPlayer
    }

    override fun onAudioListReady() {

    }

    override fun onPlayerSetupError(error: Error?) {
        Log.d(TAG,error.toString())
    }

    override fun onPlayerEvent(event: Int) {
        when (event){
            VideoPlayerEvents.PLAYER_RENDERED_FIRST_FRAME -> {
                Log.d(TAG, "Player rendered FIRST FRAME")
            }
            VideoPlayerEvents.PLAYER_STATE_IDLE -> {
                Log.d(TAG, "Player rendered IDLE STATE")
            }
            VideoPlayerEvents.PLAYER_STATE_BUFFERING -> {
                Log.d(TAG, "Player rendered BUFFERING STATE")
            }
            VideoPlayerEvents.PLAYER_STATE_PAUSED -> {
                Log.d(TAG, "Player rendered PAUSED STATE")
            }
            VideoPlayerEvents.PLAYER_STATE_READY -> {
                Log.d(TAG, "Player rendered READY STATE")
            }
            VideoPlayerEvents.PLAYER_STATE_FINISHED -> {
                Log.d(TAG, "Player rendered FINISHED STATE")
            }
        }
    }

    override fun onPlayerError(p0: Error?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        super.onPause()
        if (!playerContainer.isAudioForced) {
            videoPlayer?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!playerContainer.isAudioForced) {
            videoPlayer?.resume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!playerContainer.isAudioForced){
            videoPlayer?.stop()
            videoPlayer?.removeSetupListener(this)
            videoPlayer?.removeEventListener(this)
        }
    }
}