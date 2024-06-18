package com.bangkit.narsumku.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.R

class SpeakerAdapter(private val speakers: List<String>) :
    RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder>() {

    class SpeakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSpeakerName: TextView = itemView.findViewById(R.id.tvSpeakerName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_speaker, parent, false)
        return SpeakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
        holder.tvSpeakerName.text = speakers[position]
    }

    override fun getItemCount(): Int {
        return speakers.size
    }
}
