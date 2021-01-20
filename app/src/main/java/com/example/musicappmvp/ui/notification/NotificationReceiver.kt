package com.example.musicappmvp.ui.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.musicappmvp.ui.main.MainActivity

class NotificationReceiver : BroadcastReceiver() {
    private var songService = MainActivity.serviceSong
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            SongService.ACTION_CLOSE -> songService?.stopService()
            SongService.ACTION_PLAY -> {
                if (MainActivity.mediaMusic!!.isPlaying) MainActivity.mediaMusic?.pause()
                else MainActivity.mediaMusic?.start()
            }
            SongService.ACTION_PREV -> MainActivity.mediaMusic?.change(-1)
            SongService.ACTION_SKIP -> MainActivity.mediaMusic?.change(1)
        }
    }
}
