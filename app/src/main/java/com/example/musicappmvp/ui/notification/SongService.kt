package com.example.musicappmvp.ui.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.core.app.NotificationCompat
import com.example.musicappmvp.R
import com.example.musicappmvp.controller.MediaController
import com.example.musicappmvp.data.model.Song
import com.example.musicappmvp.ui.main.MainActivity

open class SongService : Service() {
    private var remoteView: RemoteViews? = null
    private var notificationReceiver: NotificationReceiver? = null
    private var mediaController = MainActivity.mediaMusic

    override fun onCreate() {
        super.onCreate()
        initRemoteViews()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_PREV)
        intentFilter.addAction(ACTION_PLAY)
        intentFilter.addAction(ACTION_SKIP)
        intentFilter.addAction(ACTION_CLOSE)
        registerReceiver(NotificationReceiver(), intentFilter)
    }

    private fun initRemoteViews() {
        notificationReceiver = NotificationReceiver()
        remoteView = RemoteViews(packageName, R.layout.song_notification)
        registerAction(R.id.buttonPrevNotification, ACTION_PREV)
        registerAction(R.id.buttonPauseNotification, ACTION_PLAY)
        registerAction(R.id.buttonSkipNotification, ACTION_SKIP)
        registerAction(R.id.buttonCloseNotification, ACTION_CLOSE)
    }

    private fun registerAction(@IdRes viewAction: Int, action: String) {
        val intent = Intent(action)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        remoteView?.setOnClickPendingIntent(viewAction, pendingIntent)
    }

    override fun onBind(intent: Intent?): IBinder? = SongServiceBinder(this)

    fun setSongList(songList: MutableList<Song>) {
        if (MainActivity.mediaMusic != null) {
            mediaController = object : MediaController(songList, this@SongService) {

                override fun pause() {
                    super.pause()
                    pushNotify(songList[index])
                }

                override fun start() {
                    super.start()
                    pushNotify(songList[index])
                }
            }
        }
    }

    class SongServiceBinder(private val service: SongService) : Binder() {
        fun getService(): SongService = service
    }

    private fun pushNotify(song: Song) {
        val intent = Intent(this, javaClass)
        startService(intent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        remoteView?.apply {
            setTextViewText(R.id.textSongNotification, song.title)
            setTextViewText(R.id.textArtistNotification, song.artist)
            if (MainActivity.mediaMusic!!.isPlaying) {
                remoteView?.setImageViewResource(R.id.buttonPauseNotification, R.drawable.ic_play)
            } else {
                remoteView?.setImageViewResource(R.id.buttonPauseNotification, R.drawable.ic_pause)
            }
        }

        val builder: Notification = NotificationCompat.Builder(
            this, NotificationApp.CHANNEL_SONG_ID
        ).build()

        //builder.setSmallIcon(R.drawable.ic_music)
        //builder.setCustomContentView(remoteView)
        //builder.priority = NotificationCompat.PRIORITY_HIGH
        manager.notify(1, builder)
        //startForeground(1, builder.build())
    }


    open fun stopService() {
        MainActivity.mediaMusic?.release()
        MainActivity.mediaMusic = null
        stopSelf()
    }

    companion object {
        const val ACTION_SKIP = "action.Next"
        const val ACTION_PREV = "action.Prev"
        const val ACTION_PLAY = "action.Play"
        const val ACTION_CLOSE = "action.Close"
    }
}
