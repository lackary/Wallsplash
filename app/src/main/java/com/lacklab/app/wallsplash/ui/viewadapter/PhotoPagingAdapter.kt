package com.lacklab.app.wallsplash.ui.viewadapter

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
import com.lacklab.app.wallsplash.data.model.UnsplashPhoto
import com.lacklab.app.wallsplash.databinding.ItemPhotoBinding
import timber.log.Timber
import javax.inject.Inject

class PhotoPagingAdapter @Inject constructor()
    : PagingDataAdapter<UnsplashPhoto, PhotoPagingAdapter.PhotoViewHolder>(PhotoDiffCallback) {
//    val photoClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit
//    val nameClickListener: (photoItem: UnsplashPhoto, view: View) -> Unit
    var photoClickListener: PhotoClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            DataBindingUtil.inflate<ItemPhotoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_photo,
                parent,
                false
            )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Timber.d("position: $position")
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                imageViewPhoto.setOnClickListener {
                    val photoItem = getItem(absoluteAdapterPosition)
//                    photoClickListener(photoItem!!, it)
                    photoClickListener?.onPhotoClicked(photoItem!!, it)
                }

                constraintLayoutUser.setOnClickListener {
                    val photoItem = getItem(absoluteAdapterPosition)
//                    nameClickListener(photoItem!!, it)
                    photoClickListener?.onUserClicked(photoItem!!, it)
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
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewPhoto)
                Glide.with(root)
                    .load(item.user!!.profileImage!!.large)
                    .circleCrop()
                    .override(SIZE_ORIGINAL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewUser)
            }
//            val cardViewItemLayout = binding.cardViewItem.layoutParams
//            val ratio: Float = item.height.toFloat() / item.width.toFloat()
//            Timber.d("ratio: %f", ratio)
//            cardViewItemLayout.height = (cardViewItemLayout.width.toFloat() * ratio).toInt()
//            Timber.d("height: %d", cardViewItemLayout.height)
//            Timber.d("height: %d", cardViewItemLayout.width)
//            binding.cardViewItem.layoutParams = cardViewItemLayout

        }
    }

    interface PhotoClickListener {
        fun onPhotoClicked(item: UnsplashPhoto, view: View)
        fun onUserClicked(item: UnsplashPhoto, view: View)
    }
}

private object PhotoDiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        Timber.d("areItemsTheSame")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        Timber.d("areContentsTheSame")
        return oldItem == newItem
    }
}
