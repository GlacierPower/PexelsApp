package com.glacierpower.pexelsapp.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.pexelsapp.databinding.SearchItemBinding
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.presentation.adapter.listener.FeaturedListener


class FeaturedAdapter(private val featuredListener: FeaturedListener) :
    RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder>() {

    private var selectedItemPosition = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeaturedAdapter.FeaturedViewHolder {
        return FeaturedViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeaturedAdapter.FeaturedViewHolder, position: Int) {
        val featured = differ.currentList[position]
        holder.bind(featured)
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyItemChanged(position)

        }
        if(selectedItemPosition == position){
            holder.card.setBackgroundColor(Color.parseColor("#BB1020"))
        }
        else{
            holder.card.setBackgroundColor(Color.parseColor("#ffffff"))

        }
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<CollectionModel>() {
            override fun areItemsTheSame(
                oldItem: CollectionModel,
                newItem: CollectionModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CollectionModel,
                newItem: CollectionModel
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FeaturedViewHolder(private val searchItemBinding: SearchItemBinding) :
        RecyclerView.ViewHolder(searchItemBinding.root) {
            val card = searchItemBinding.cardView
        fun bind(featured: CollectionModel) {
            searchItemBinding.apply {
                searchItemBinding.categoryName.text = featured.title
            }
            searchItemBinding.categoryName.setOnClickListener {
                featuredListener.getFeaturedPhoto(featured.title)

            }
        }
    }
}