package com.lacklab.app.wallsplash.viewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.data.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ItemGalleryBinding
import com.lacklab.app.wallsplash.view.GalleryFragmentDirections
import timber.log.Timber

class ImageAdapter(
    private val photoClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit,
//    private val nameClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit
) :
    PagingDataAdapter<UnsplashPhoto, ImageAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding =
            DataBindingUtil.inflate<ItemGalleryBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_gallery,
                parent,
                false
            )
//        val viewBinding =
//            ItemGalleryBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Timber.d("position: $position")
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class GalleryViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageViewPhoto.setOnClickListener {
                val photoItem = getItem(absoluteAdapterPosition)
                photoClickListener(photoItem!!, it)
            }

//            binding.textViewName.setOnClickListener {
//                val photoItem = getItem(absoluteAdapterPosition)
//                nameClickListener(photoItem!!, it)
//            }
        }

        fun bind(item:UnsplashPhoto) {
            with(binding) {
                photoItem = item
            }
            val url = item.urls.regular
            Glide.with(binding.root)
                .load(url)
                .override(SIZE_ORIGINAL)
                .into(binding.imageViewPhoto)
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
