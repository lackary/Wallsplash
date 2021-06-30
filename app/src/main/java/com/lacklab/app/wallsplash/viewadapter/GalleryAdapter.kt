package com.lacklab.app.wallsplash.viewadapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ItemGalleryBinding

class GalleryAdapter :
    PagingDataAdapter<UnsplashPhoto, GalleryAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val viewBinding =
            ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return GalleryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val gallery = getItem(position)
        Log.i("GalleryAdapter", "position: $position")
        if (gallery != null) {
            holder.bind(gallery)
        }
    }

    class GalleryViewHolder(private val viewBinding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private lateinit var unsplashPhoto: UnsplashPhoto
            init {
                viewBinding.imageViewPhoto.setOnClickListener {

                }
            }

            fun bind(item:UnsplashPhoto) {
                unsplashPhoto = item
                val url = item.urls.regular
                Glide.with(viewBinding.root)
                    .load(url)
                    .into(viewBinding.imageViewPhoto)
            }

    }
}

private class GalleryDiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem == newItem
    }
}
