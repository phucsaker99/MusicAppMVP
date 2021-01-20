package com.example.musicappmvp.data.source.local.dao

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
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
            val indexId = it.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID)
            val indexSize = it.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)
            val indexTitle = it.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
            val indexArtist = it.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)
            val indexAlbum = it.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)
            var indexDuration = -1
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
                indexDuration = it.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
            }
            val indexData = it.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
            while (!it.isAfterLast) {
                songList.add(
                    Song(
                        it.getLong(indexId),
                        it.getInt(indexSize),
                        it.getString(indexTitle),
                        it.getString(indexArtist),
                        it.getString(indexAlbum),
                        it.getInt(indexDuration),
                        it.getString(indexData)
                    )
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
            val indexId = it.getColumnIndex(MediaStore.Audio.Artists._ID)
            val indexArtist = it.getColumnIndex(MediaStore.Audio.Artists.ARTIST)
            val indexCountAlbum = it.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
            val indexCountSong = it.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)

            while (!it.isAfterLast) {
                artistList.add(
                    Artist(
                        it.getLong(indexId),
                        it.getString(indexArtist),
                        it.getInt(indexCountAlbum),
                        it.getInt(indexCountSong)
                    )
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
