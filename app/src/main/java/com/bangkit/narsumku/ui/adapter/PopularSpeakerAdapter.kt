package com.bangkit.narsumku.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.databinding.ItemPopularSpeakerBinding
import com.bumptech.glide.Glide

class PopularSpeakerAdapter(
    private val speakers: List<PopularSpeaker>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PopularSpeakerAdapter.PopularViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String, optionsCompat: ActivityOptionsCompat)
    }

    inner class PopularViewHolder(private val binding: ItemPopularSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: PopularSpeaker) {
            Glide.with(binding.root.context)
                .load(speaker.profilePicUrl)
                .into(binding.ivSpeakerPhoto)
            binding.tvSpeakerName.text = speaker.fullName
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.tvSpeakerExperience.text = speaker.experience
            binding.tvSpeakerFavorite.text = speaker.favoriteCount.toString()

            binding.root.setOnClickListener {
                val context = itemView.context as Activity
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    Pair(binding.ivSpeakerPhoto, "speakerAvatar"),
                    Pair(binding.tvSpeakerName, "speakerName"),
                    Pair(binding.tvSpeakerExperience, "speakerExperience")
                )
                itemClickListener.onItemClick(speaker.speakerId, optionsCompat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val binding =
            ItemPopularSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size
}