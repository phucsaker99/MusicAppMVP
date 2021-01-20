package com.example.musicappmvp.data.source.repository

import com.example.musicappmvp.data.model.Artist
import com.example.musicappmvp.data.model.Song

interface MusicDataSource {
    interface Local {
        interface Resolver{
            fun getSongList(): MutableList<Song>
            fun getArtistList(): MutableList<Artist>
        }
    }

    interface Remote {
        
    }
}
