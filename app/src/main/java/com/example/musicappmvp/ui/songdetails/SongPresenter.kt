package com.example.musicappmvp.ui.songdetails

import com.example.musicappmvp.data.model.Song
import com.example.musicappmvp.data.source.repository.MusicRepository

class SongPresenter(
        private val songView: SongContract.View,
        private val songRepository: MusicRepository
) : SongContract.Presenter {
    override fun getSongList(): MutableList<Song> {
        songView.showSongList(songRepository.getSongList())
        return songRepository.getSongList()
    }
}