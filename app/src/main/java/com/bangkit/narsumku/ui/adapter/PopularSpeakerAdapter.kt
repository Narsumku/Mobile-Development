package com.bangkit.narsumku.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.databinding.ItemPopularSpeakerBinding

class PopularSpeakerAdapter(
    private val speakers: List<PopularSpeaker>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PopularSpeakerAdapter.PopularViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String)
    }

    inner class PopularViewHolder(private val binding: ItemPopularSpeakerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: PopularSpeaker) {
            binding.tvSpeakerName.text = speaker.fullName
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.tvSpeakerExperience.text = speaker.experience
            binding.tvSpeakerFavorite.text = speaker.favoriteCount.toString()
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(speaker.speakerId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding = ItemPopularSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size
}