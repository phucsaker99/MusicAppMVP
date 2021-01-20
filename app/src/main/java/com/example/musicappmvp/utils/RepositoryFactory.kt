package com.example.musicappmvp.utils

import android.content.Context
import com.example.musicappmvp.data.source.local.dao.ResolverDataImp
import com.example.musicappmvp.data.source.remote.MusicApiImp
import com.example.musicappmvp.data.source.repository.MusicRepository

object RepositoryFactory {
    fun getRepository(context: Context): MusicRepository {
        val resolverDataImp = ResolverDataImp.getInstance(context)
        return MusicRepository.getInstance(resolverDataImp)
    }
}
