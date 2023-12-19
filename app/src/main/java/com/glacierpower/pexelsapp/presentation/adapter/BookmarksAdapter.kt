package com.glacierpower.pexelsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.data.data_base.BookmarksEntity
import com.glacierpower.pexelsapp.databinding.BookmarksItemBinding
import com.glacierpower.pexelsapp.presentation.adapter.listener.BookmarksListener
import com.glacierpower.pexelsapp.utils.Constants.PATH
import com.glacierpower.pexelsapp.utils.Constants.PHOTO
import com.glacierpower.pexelsapp.utils.Constants.TYPE

class BookmarksAdapter(private val bookmarksListener: BookmarksListener) :
    RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarksAdapter.BookmarksViewHolder {
        return BookmarksViewHolder(
            BookmarksItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarksAdapter.BookmarksViewHolder, position: Int) {
        val photo = differ.currentList[position]
        holder.bind(photo)
    }

    private val differCallback =
        object : DiffUtil.ItemCallback<BookmarksEntity>() {
            override fun areItemsTheSame(
                oldItem: BookmarksEntity,
                newItem: BookmarksEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BookmarksEntity,
                newItem: BookmarksEntity
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {

        return differ.currentList.size

    }

    inner class BookmarksViewHolder(
        private val bookmarksItemBinding: BookmarksItemBinding,

        ) :
        RecyclerView.ViewHolder(bookmarksItemBinding.root) {
        fun bind(bookmarksEntity: BookmarksEntity) {

            val id = bookmarksEntity.id
            bookmarksItemBinding.apply {
                Glide.with(bookmarksItemBinding.photo)
                    .load(
                        "$PATH$id$PHOTO$id$TYPE"
                    )
                    .placeholder(R.drawable.place_holder)
                    .into(bookmarksItemBinding.photo)

                this.tvAuthor.text = bookmarksEntity.photographer

            }
            bookmarksItemBinding.photo.setOnClickListener {
                bookmarksListener.getPhotoById(
                    id,
                    "$PATH$id$PHOTO$id$TYPE"
                )
            }

        }
    }
}