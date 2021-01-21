package com.example.musicappmvp.data.source.local.dao

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.example.musicappmvp.data.model.Artist
import com.example.musicappmvp.data.model.Song
import com.example.musicappmvp.data.source.repository.MusicDataSource

class ResolverDataImp(context: Context) : MusicDataSource.Local.Resolver {
    private var contentResolver: ContentResolver? = null

    init {
        contentResolver = context.contentResolver
    }

    @SuppressLint("Recycle")
    override fun getSongList(): MutableList<Song> {
        val songList: MutableList<Song> = mutableListOf()
        val cursor = contentResolver?.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor!!.let {
            while (!it.isAfterLast) {
                songList.add(
                        Song(it)
                )
                it.moveToNext()
            }
            it.close()
            songList
        }
    }

    @SuppressLint("Recycle")
    override fun getArtistList(): MutableList<Artist> {
        val artistList: MutableList<Artist> = mutableListOf()
        val cursor = contentResolver?.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor!!.let {
            while (!it.isAfterLast) {
                artistList.add(
                        Artist(it)
                )
                it.moveToNext()
            }
            it.close()
            artistList
        }
    }

    companion object {
        private var instance: ResolverDataImp? = null
        fun getInstance(context: Context) = instance ?: ResolverDataImp(context).also {
            instance = it
        }
    }
}
