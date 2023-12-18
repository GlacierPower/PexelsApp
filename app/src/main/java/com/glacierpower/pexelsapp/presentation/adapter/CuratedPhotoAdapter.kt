package com.glacierpower.pexelsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.databinding.PhotoItemBinding
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.presentation.adapter.listener.CuratedListener


class CuratedPhotoAdapter(private val curatedListener: CuratedListener) :
    RecyclerView.Adapter<CuratedPhotoAdapter.CuratedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CuratedPhotoAdapter.CuratedViewHolder {
        return CuratedViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CuratedPhotoAdapter.CuratedViewHolder, position: Int) {
        val featured = differ.currentList[position]
        holder.bind(featured)

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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class CuratedViewHolder(private val photoItemBinding: PhotoItemBinding) :
        RecyclerView.ViewHolder(photoItemBinding.root) {
        fun bind(photo: PhotoListModel) {
            photoItemBinding.apply {
                Glide.with(itemView)
                    .load(photo.src.medium)
                    .placeholder(R.drawable.place_holder)
                    .into(photoItemBinding.photo)

                photoItemBinding.photo.setOnClickListener {
                    curatedListener.getPhotoById(photo.id, photo.src.original)
                }
            }
        }
    }
}