package com.example.musicappmvp.ui.songdetails

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.musicappmvp.R
import com.example.musicappmvp.controller.MediaController
import com.example.musicappmvp.data.model.Song
import com.example.musicappmvp.data.source.local.dao.ResolverDataImp
import com.example.musicappmvp.data.source.remote.MusicApiImp
import com.example.musicappmvp.data.source.repository.MusicRepository
import com.example.musicappmvp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_detail_song.*

class SongDetailFragment : Fragment(R.layout.fragment_detail_song), SongContract.View {
    private lateinit var songAdapter: SongAdapter
    private var songPresenter: SongPresenter? = null
    private var indexSong = -1
    private var songList: MutableList<Song> = mutableListOf()


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initView() {
        songPresenter = context?.let { ResolverDataImp(it) }?.let {
            MusicRepository(
                it, MusicApiImp(
                    context!!
                )
            )
        }?.let {
            SongPresenter(
                this,
                it
            )
        }

        songAdapter = SongAdapter(context!!)
        songAdapter.listenerClick = this::clickListenerItemSong
        songList = songPresenter?.getSongList()!!
        recyclerSong.adapter = songAdapter
        MainActivity.mediaMusic = MediaController(songList, context!!)
    }

    override fun showSongList(songList: MutableList<Song>) {
        this.songList = songList
        songAdapter.songList = songList
        songAdapter.notifyDataSetChanged()
    }

    private fun clickListenerItemSong(song: Song) {
        val mainActivity = activity as MainActivity
        indexSong = songList.indexOf(song)
        MainActivity.mediaMusic?.create(indexSong)
        mainActivity.getService()?.setSongList(songAdapter.songList)
        MainActivity.mediaMusic?.create(songAdapter.songList.indexOf(song))
    }
}


