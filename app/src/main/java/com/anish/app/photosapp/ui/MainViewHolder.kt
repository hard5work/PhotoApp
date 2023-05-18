package com.anish.app.photosapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anish.app.photosapp.model.PhotoModel

class MainViewHolder : ViewModel() {

    private lateinit var mPhotos: ArrayList<PhotoModel>
    private var newPhotoList = ArrayList<PhotoModel>()
    private var imageList = MutableLiveData<ArrayList<PhotoModel>>()
    val _imageList: MutableLiveData<ArrayList<PhotoModel>>
        get() = imageList

    private fun generateTriangularSequence(n: Int) {
        val sequence = mutableListOf<Int>()
        var sum = 0

        //generating triangular indexes
        for (i in 1..n) {
            sum += i
            sequence.add(sum)
        }

        //inserting photo according to sequence data
        for (j in 1..n) {
            if (sequence.contains(j)) {
                newPhotoList.add(mPhotos[0])
            } else {

                newPhotoList.add(mPhotos[1])
            }
        }
        imageList.postValue(newPhotoList)
    }

    fun setTriangularSequence(n: Int, mPhoto: ArrayList<PhotoModel>) {
        newPhotoList.clear()
        this.mPhotos = mPhoto
        generateTriangularSequence(n)

    }

}