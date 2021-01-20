package com.example.musicappmvp.ui.songdetails

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicappmvp.R
import com.example.musicappmvp.data.model.Song
import kotlinx.android.synthetic.main.item_song.view.*

class SongAdapter(private val context: Context) : RecyclerView.Adapter<SongAdapter.SongHolder>() {
    var listenerClick: (Song) -> Unit = { _ -> }

    var songList: MutableList<Song> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder =
        SongHolder(LayoutInflater.from(context).inflate(R.layout.item_song, parent, false))

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: SongHolder, position: Int) =
        holder.bind(songList[position])

    inner class SongHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(song: Song) {
            itemView.apply {
                textTitleSong.text = song.title
                textArtist.text = song.artist
                val uri = Uri.parse("content://media/external/audio/albumart/" + song.id)
                imageSong.setImageURI(uri)
                setOnClickListener {
                    listenerClick(
                        song
                    )
                }
            }
        }
    }
}
