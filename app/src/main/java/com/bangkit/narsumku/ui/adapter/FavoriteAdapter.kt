package com.bangkit.narsumku.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.GetFavoriteResponse
import com.bangkit.narsumku.databinding.ItemRecommendationSpeakerBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private var speakers: MutableList<GetFavoriteResponse>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String, optionsCompat: ActivityOptionsCompat)
    }

    inner class FavoriteViewHolder(private val binding: ItemRecommendationSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: GetFavoriteResponse) {
            Glide.with(binding.root.context)
                .load(speaker.profilePicUrl)
                .into(binding.ivSpeakerPhoto)
            binding.tvSpeakerName.text = speaker.name
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.tvSpeakerExperience.text = speaker.experience
            binding.tvSpeakerField.text = speaker.field
            binding.root.setOnClickListener {
                val context = itemView.context as Activity
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    Pair(binding.ivSpeakerPhoto, "speakerAvatar"),
                    Pair(binding.tvSpeakerName, "speakerName"),
                    Pair(binding.tvSpeakerExperience, "speakerExperience"),
                    Pair(binding.tvSpeakerField, "speakerCategory")
                )
                itemClickListener.onItemClick(speaker.speakerId, optionsCompat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRecommendationSpeakerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newSpeakers: List<GetFavoriteResponse>) {
        speakers.clear()
        speakers.addAll(newSpeakers)
        notifyDataSetChanged()
    }
}