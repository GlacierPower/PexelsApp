package com.glacierpower.pexelsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.databinding.DetailsItemBinding
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.presentation.adapter.listener.DetailsListener

class DetailsAdapter(private val detailsListener: DetailsListener) :
    RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    inner class DetailsViewHolder(private val detailsItemBinding: DetailsItemBinding) :
        RecyclerView.ViewHolder(detailsItemBinding.root) {
        fun bind(photoListModel: PhotoListModel) {

            detailsItemBinding.apply {
                Glide.with(itemView)
                    .load(photoListModel.src.original)
                    .placeholder(R.drawable.place_holder)
                    .into(detailsItemBinding.photo)

            }
            detailsItemBinding.btnDownload.setOnClickListener {
                detailsListener.downloadPhoto(photoListModel.src.original)
            }
            detailsItemBinding.btnBookmark.setOnClickListener {
                detailsListener.addToBookmarks(photoListModel.id)

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsAdapter.DetailsViewHolder {
        return DetailsViewHolder(
            DetailsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val liveEvent = differ.currentList[position]
        holder.bind(liveEvent)
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<PhotoListModel>() {
            override fun areItemsTheSame(
                oldItem: PhotoListModel,
                newItem: PhotoListModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PhotoListModel,
                newItem: PhotoListModel
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)
}