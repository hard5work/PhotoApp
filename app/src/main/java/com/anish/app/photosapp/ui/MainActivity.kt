package com.anish.app.photosapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anish.app.photosapp.R
import com.anish.app.photosapp.databinding.ActivityMainBinding
import com.anish.app.photosapp.databinding.LayoutAlertForSizeBinding
import com.anish.app.photosapp.model.PhotoModel
import com.anish.app.photosapp.others.AlertInterfaces
import com.anish.app.photosapp.others.DynamicAlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewHolder
    private lateinit var adapter: PhotoAdapter
    private var mPhoto = ArrayList<PhotoModel>()
    private var showPhotos = ArrayList<PhotoModel>()
    private var arraySize = 0
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewHolder::class.java]
        binding.choosePhotoBtn.setOnClickListener {
            chooseSizeForArray()
        }
        adapter = PhotoAdapter(this@MainActivity, showPhotos)
        binding.imageRecyclerView.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
        binding.imageRecyclerView.adapter = adapter
        imageListObserver()
    }

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        imagePickerLauncher.launch(intent)
    }

    private fun chooseSizeForArray() {
        DynamicAlertDialog(
            this@MainActivity,
            R.layout.layout_alert_for_size,
            object : AlertInterfaces {
                override fun onViewCreated(view: View, dialog: AlertDialog) {
                    val bind = LayoutAlertForSizeBinding.bind(view)
                    bind.doneButton.setOnClickListener {
                        if (bind.enterNumber.text.isNullOrEmpty()) {
                            bind.enterNumber.error = "Please provide any number."
                        } else {
                            bind.enterNumber.error = null
                            arraySize = bind.enterNumber.text.toString().toInt()
                            openGallery()
                            dialog.dismiss()

                        }
                    }
                }

            }
        )
    }

    private fun imageListObserver() {
        viewModel._imageList.observe(this) {

            showPhotos.clear()
            showPhotos.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImages = data?.clipData
                mPhoto.clear()
                if (selectedImages != null && selectedImages.itemCount >= 2) {
                    // Process the selected images here
                    for (i in 0 until 2) {
                        val imageUri = selectedImages.getItemAt(i).uri
                        val photoModel = PhotoModel(imageUri)
                        mPhoto.add(photoModel)
                    }
                    viewModel.setTriangularSequence(arraySize, mPhoto)
                } else {
                    Log.e(TAG, "more than 2 images selected")
                }
            }
        }
}