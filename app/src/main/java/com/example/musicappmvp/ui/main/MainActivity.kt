package com.example.musicappmvp.ui.main

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.musicappmvp.R
import com.example.musicappmvp.controller.MediaController
import com.example.musicappmvp.data.source.local.dao.ResolverDataImp
import com.example.musicappmvp.ui.artistdetails.ArtistDetailFragment
import com.example.musicappmvp.ui.notification.SongService
import com.example.musicappmvp.ui.songdetails.SongDetailFragment
import com.example.musicappmvp.utils.NewsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapterPage: NewsPagerAdapter? = null
    private val fmSong: SongDetailFragment = SongDetailFragment()
    private val fmArtist: ArtistDetailFragment = ArtistDetailFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkPermission()) {
            initView()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(PERMISSION, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connectionService)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkPermission()) {
            initView()
        }
    }

    fun getService(): SongService? = serviceSong

    private fun initView() {
        serviceSong = SongService()
        val intent = Intent(this, SongService::class.java)
        bindService(intent, connectionService, Context.BIND_AUTO_CREATE)
        resolverMusic = ResolverDataImp(this)
        mediaMusic = MediaController.getInstance(resolverMusic.getSongList(), this)
        adapterPage = NewsPagerAdapter(supportFragmentManager, fmSong, fmArtist)
        viewPagerMusic.adapter = adapterPage
        tabLayoutMusic.setupWithViewPager(viewPagerMusic)
    }

    private var connectionService = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val songBinder = service as SongService.SongServiceBinder
            serviceSong = songBinder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (p in PERMISSION) {
                val status = checkSelfPermission(p)
                if (status == PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
        }
        return true
    }

    companion object {
        lateinit var resolverMusic: ResolverDataImp
        var mediaMusic: MediaController? = null
        val PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        var serviceSong: SongService? = null
    }
}
