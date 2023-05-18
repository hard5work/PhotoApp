package com.anish.app.photosapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anish.app.photosapp.databinding.LayoutPhotoViewBinding
import com.anish.app.photosapp.model.PhotoModel
import com.bumptech.glide.Glide

class PhotoAdapter(
    private val context: Context,
    private val mList: ArrayList<PhotoModel>
) : Adapter<PhotoAdapter.PhotoViewHolder>() {
    inner class PhotoViewHolder(item: LayoutPhotoViewBinding) : ViewHolder(item.root) {
        val bind = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            LayoutPhotoViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = mList.size
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val mPhoto = mList[position]
        val index = "Index : ${position + 1}"
        holder.bind.indexNumber.text = index
        Glide.with(context).load(mPhoto.photo).into(holder.bind.imageView)
    }
}