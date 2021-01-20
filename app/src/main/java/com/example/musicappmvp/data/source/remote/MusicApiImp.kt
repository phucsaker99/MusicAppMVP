package com.example.musicappmvp.data.source.remote

import android.content.Context
import com.example.musicappmvp.data.source.repository.MusicDataSource

class MusicApiImp(context: Context){

    companion object {
        private var instance: MusicApiImp? = null
        fun getInstance(context: Context) = instance?: MusicApiImp(context).also {
            instance = it
        }
    }
}
