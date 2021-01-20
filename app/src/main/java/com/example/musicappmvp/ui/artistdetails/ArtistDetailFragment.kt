package com.example.musicappmvp.ui.artistdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.musicappmvp.R
import com.example.musicappmvp.data.model.Artist
import com.example.musicappmvp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_detail_artist.*

class ArtistDetailFragment : Fragment(R.layout.fragment_detail_artist) {
    private var artistList: MutableList<Artist>?= null
    private var adapterArtist: ArtistAdapter?= null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        adapterArtist = context?.let { ArtistAdapter(it) }
        artistList = MainActivity.resolverMusic.getArtistList()
        adapterArtist?.artistList = artistList!!
        recyclerArtist.adapter = adapterArtist
    }
}
