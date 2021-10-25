package com.lacklab.app.wallsplash.viewadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.data.UnsplashCollection
import com.lacklab.app.wallsplash.databinding.ItemCollectionBinding
import timber.log.Timber

class CollectionPagingAdapter(
    private val photoClickListener: (photoItem: UnsplashCollection, view: View) -> Unit,
    private val nameClickListener: (photoItem: UnsplashCollection, view: View) -> Unit
) : PagingDataAdapter<
        UnsplashCollection,
        CollectionPagingAdapter.CollectionViewHolder
    >(CollectionDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionViewHolder {
        val binding = DataBindingUtil.inflate<ItemCollectionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_collection,
            parent,
            false
        )
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        Timber.d("position: $position")
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class CollectionViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                with(binding) {
                    imageViewPhoto.setOnClickListener {
                        val photoItem = getItem(absoluteAdapterPosition)
                        photoClickListener(photoItem!!, it)
                    }

                    binding.textViewName.setOnClickListener {
                        val photoItem = getItem(absoluteAdapterPosition)
                        nameClickListener(photoItem!!, it)
                    }
                }
            }

            fun bind(item: UnsplashCollection) {
                with(binding) {
                    collectionItem = item

                    Glide.with(root)
                        .load(item.coverPhoto.urls!!.regular)
                        .centerCrop()
                        .fitCenter()
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageViewPhoto)
                    Glide.with(root)
                        .load(item.user!!.profileImage!!.large)
                        .circleCrop()
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageViewUser)
                }
            }
        }
}

private class CollectionDiffCallback : DiffUtil.ItemCallback<UnsplashCollection>() {
    override fun areItemsTheSame(oldItem: UnsplashCollection, newItem: UnsplashCollection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashCollection, newItem: UnsplashCollection): Boolean {
        return oldItem == newItem
    }
}