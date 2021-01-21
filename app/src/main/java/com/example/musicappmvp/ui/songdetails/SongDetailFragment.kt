package com.example.musicappmvp.ui.songdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.musicappmvp.R
import com.example.musicappmvp.controller.MediaController
import com.example.musicappmvp.data.model.Song
import com.example.musicappmvp.data.source.local.dao.ResolverDataImp
import com.example.musicappmvp.data.source.repository.MusicRepository
import com.example.musicappmvp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_detail_song.*

class SongDetailFragment : Fragment(R.layout.fragment_detail_song), SongContract.View {
    private var songAdapter = SongAdapter()
    private var songPresenter: SongPresenter? = null
    private var indexSong = -1
    private var songs: MutableList<Song> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        songPresenter = context?.let { ResolverDataImp(it) }?.let {
            MusicRepository(it)
        }?.let {
            SongPresenter(
                this,
                it
            )
        }
        songAdapter.listenerClick = this::clickListenerItemSong
        songs = songPresenter?.getSongList()!!
        recyclerSong.adapter = songAdapter
        MainActivity.mediaMusic = MediaController(songs, context!!)
    }

    override fun showSongList(songList: MutableList<Song>) {
        this.songs = songList
        songAdapter.setSongList(songList)
        songAdapter.notifyDataSetChanged()
    }

    private fun clickListenerItemSong(song: Song) {
        val mainActivity = activity as MainActivity
        indexSong = songs.indexOf(song)
        MainActivity.mediaMusic?.create(indexSong)
        mainActivity.getService()?.setSongList(songAdapter.getSongList())
        MainActivity.mediaMusic?.create(songAdapter.getSongList().indexOf(song))
    }
}


