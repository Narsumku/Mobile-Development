//package com.bangkit.narsumku.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bangkit.narsumku.data.response.RecommendedSpeaker
//import com.bangkit.narsumku.databinding.ItemRecommendedSpeakerBinding
//
//class RecommendedSpeakerAdapter(
//    private val speakers: List<RecommendedSpeaker>,
//    private val itemClickListener: OnItemClickListener
//) : RecyclerView.Adapter<RecommendedSpeakerAdapter.RecommendedViewHolder>() {
//
//    interface OnItemClickListener {
//        fun onItemClick(speakerId: String)
//    }
//
//    inner class RecommendedViewHolder(private val binding: ItemRecommendedSpeakerBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(speaker: RecommendedSpeaker) {
//            binding.tvSpeakerName.text = speaker.fullName
//            binding.tvSpeakerRating.text = speaker.rating.toString()
//            binding.tvSpeakerExperience.text = speaker.experience
//            binding.root.setOnClickListener {
//                itemClickListener.onItemClick(speaker.speakerId)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
//        // Inflate layout item
//        val binding = ItemRecommendedSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return RecommendedViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
//        val speaker = speakers[position]
//        holder.bind(speaker)
//    }
//
//    override fun getItemCount(): Int = speakers.size
//}