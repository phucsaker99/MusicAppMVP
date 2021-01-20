package com.example.musicappmvp.ui.songdetails

import com.example.musicappmvp.data.model.Song

interface SongContract {
    interface View{
        fun showSongList(songList: MutableList<Song>)
    }

    interface Presenter{
        fun getSongList(): MutableList<Song>
    }
}
