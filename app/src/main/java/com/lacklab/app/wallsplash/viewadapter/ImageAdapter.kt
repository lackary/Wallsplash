package com.lacklab.app.wallsplash.viewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ItemGalleryBinding
import timber.log.Timber

class ImageAdapter :
    PagingDataAdapter<UnsplashPhoto, ImageAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val viewBinding =
            ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return GalleryViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Timber.d("position: $position")
        getItem(position)?.let {
            holder.bind(it)
        }
//        val gallery = getItem(position)
//        if (gallery != null) {
//            holder.bind(gallery)
//        }
    }

    inner class GalleryViewHolder(private val viewBinding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private lateinit var unsplashPhoto: UnsplashPhoto
        init {
            viewBinding.imageViewPhoto.setOnClickListener {
                val photoItem = getItem(absoluteAdapterPosition)
            }
        }

        fun bind(item:UnsplashPhoto) {
            unsplashPhoto = item
            val url = item.urls.regular
            Glide.with(viewBinding.root)
                .load(url)
                .override(SIZE_ORIGINAL)
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
