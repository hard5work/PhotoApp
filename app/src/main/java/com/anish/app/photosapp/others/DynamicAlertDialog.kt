package com.anish.app.photosapp.others

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater


class DynamicAlertDialog(
    context: Context,
    layout: Int,
    alertInterfaces: AlertInterfaces,
    cancellable:Boolean = true
) : DialogInterface {
    private var builder: AlertDialog

    init {
        val alertDialog = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(layout, null)
        alertDialog.setView(view)
        alertDialog.setCancelable(cancellable)
        builder = alertDialog.create()
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.show()
        alertInterfaces.onViewCreated(view, builder)


    }

    override fun cancel() {
        builder.dismiss()
    }

    override fun dismiss() {
        builder.dismiss()
    }
}
