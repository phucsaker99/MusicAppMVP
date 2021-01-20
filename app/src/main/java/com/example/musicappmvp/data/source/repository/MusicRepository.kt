package com.example.musicappmvp.data.source.repository

import com.example.musicappmvp.data.model.Artist
import com.example.musicappmvp.data.model.Song

class MusicRepository constructor(
    private val local: MusicDataSource.Local.Resolver,
    private val remote: MusicDataSource.Remote
) : MusicDataSource.Local.Resolver, MusicDataSource.Remote {
    override fun getSongList(): MutableList<Song> = local.getSongList()

    override fun getArtistList(): MutableList<Artist> = local.getArtistList()

    companion object {
        private var instance: MusicRepository? = null
        fun getInstance(local: MusicDataSource.Local.Resolver, remote: MusicDataSource.Remote) =
            instance?: MusicRepository(local, remote).also { instance = it }
    }
}
