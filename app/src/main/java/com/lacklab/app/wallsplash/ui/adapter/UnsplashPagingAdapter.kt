package com.lacklab.app.wallsplash.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.lacklab.app.wallsplash.R
import com.lacklab.app.wallsplash.data.model.UnsplashCollection
import com.lacklab.app.wallsplash.util.UnsplashItem
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ItemCollectionBinding
import com.lacklab.app.wallsplash.databinding.ItemPhotoBinding
import javax.inject.Inject

class UnsplashPagingAdapter @Inject constructor()
    : PagingDataAdapter<UnsplashItem, RecyclerView.ViewHolder>(ItemDiffCallback) {
//    val photoClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit
//    val nameClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_photo -> {
                val binding =
                    DataBindingUtil.inflate<ItemPhotoBinding>(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    )
                PhotoViewHolder(binding)
            }
            R.layout.item_collection -> {
                val binding =
                    DataBindingUtil.inflate<ItemCollectionBinding>(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    )
                CollectionViewHolder(binding)
            }
            else -> {
                val binding =
                    DataBindingUtil.inflate<ItemPhotoBinding>(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                    )
                PhotoViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when(holder) {
                is PhotoViewHolder -> holder.bind((it as UnsplashItem.Photo).data)
                is CollectionViewHolder -> holder.bind((it as UnsplashItem.Collection).data)
            }
        }
    }

    override fun getItemViewType(position: Int) =
        when(getItem(position)) {
            is UnsplashItem.Photo -> R.layout.item_photo
            is UnsplashItem.Collection -> R.layout.item_collection
            null -> throw IllegalStateException("Unknown view")
        }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                imageViewPhoto.setOnClickListener {
                    val photoItem = getItem(absoluteAdapterPosition)
//                    photoClickListener(photoItem!!, it)
                    itemClickListener?.onPhotoClicked(photoItem!!, it)
                }

                constraintLayoutUser.setOnClickListener {
                    val photoItem = getItem(absoluteAdapterPosition)
//                    nameClickListener(photoItem!!, it)
                    itemClickListener?.onUserClicked(photoItem!!, it)
                }
            }
        }

        fun bind(item: UnsplashPhoto) {
            with(binding) {
                photoItem = item

                Glide.with(root)
                    .load(item.urls!!.regular)
                    .centerCrop()
                    .fitCenter()
                    .override(SIZE_ORIGINAL)
                    .placeholder(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewPhoto)
                Glide.with(root)
                    .load(item.user!!.profileImage!!.large)
                    .circleCrop()
                    .override(SIZE_ORIGINAL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewUser)
            }
        }
    }

    inner class CollectionViewHolder(private val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                with(binding) {
                    imageViewPhoto.setOnClickListener {
                        val collectionItem = this@UnsplashPagingAdapter.getItem(absoluteAdapterPosition)
                        itemClickListener?.onPhotoClicked(collectionItem!!, it)
        //                        photoClickListener(photoItem!!, it)
                    }

                    constraintLayoutUser.setOnClickListener {
                        val collectionItem = getItem(absoluteAdapterPosition)
                        itemClickListener?.onPhotoClicked(collectionItem!!, it)
        //                        nameClickListener(photoItem!!, it)
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
                        .override(SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageViewPhoto)
                Glide.with(root)
                    .load(item.user!!.profileImage!!.large)
                    .circleCrop()
                    .override(SIZE_ORIGINAL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewUser)
            }
        }
    }

    interface ItemClickListener {
        fun onPhotoClicked(item: UnsplashItem, view: View)
        fun onUserClicked(item: UnsplashItem, view: View)
    }
}

private object ItemDiffCallback : DiffUtil.ItemCallback<UnsplashItem>() {
    override fun areItemsTheSame(oldItem: UnsplashItem, newItem: UnsplashItem): Boolean {
        val isSamePhoto = oldItem is UnsplashItem.Photo
                && newItem is UnsplashItem.Photo
                && oldItem.data.id == newItem.data.id
        val isSameCollection = oldItem is UnsplashItem.Collection
                && newItem is UnsplashItem.Collection
                && oldItem.data.id == newItem.data.id
        return isSamePhoto  || isSameCollection
    }

    override fun areContentsTheSame(oldItem: UnsplashItem, newItem: UnsplashItem): Boolean {
        return oldItem == newItem
    }
}
