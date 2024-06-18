package com.bangkit.narsumku.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.SearchResponse
import com.bangkit.narsumku.data.response.Speaker
import com.bangkit.narsumku.databinding.ItemSpeakerBinding

class SearchAdapter(
    private val speakers: List<Speaker>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String)
    }

    inner class SearchViewHolder(private val binding: ItemSpeakerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: Speaker) {
            binding.tvSpeakerName.text = speaker.name
            if(speaker.field == "Media_News") {
                binding.tvSpeakerField.text = "Media & News"
            } else {
                binding.tvSpeakerField.text = speaker.field
            }
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(speaker.speakerId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size
}