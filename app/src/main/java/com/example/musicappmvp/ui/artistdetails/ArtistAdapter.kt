package com.example.musicappmvp.ui.artistdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicappmvp.R
import com.example.musicappmvp.data.model.Artist
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistAdapter(private val context: Context) :
    RecyclerView.Adapter<ArtistAdapter.ArtistHolder>() {
    var artistList = mutableListOf<Artist>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false)
        return ArtistHolder(view)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ArtistHolder, position: Int) {
        holder.bind(artistList[position])
    }

    class ArtistHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(artist: Artist) {
            itemView.apply {
                textTitleArtist.text = artist.artist
                textCountSong.text = artist.countSong.toString()
            }
        }
    }
}
