package ru.infoshell.contactsapp.presentation.ext

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import ru.infoshell.contactsapp.R

@BindingAdapter("loadFile")
fun ImageView.loadFile(uri: String?) {

    Log.e("BindingImg", "$uri")
    if (!uri.isNullOrEmpty())
        Picasso.get()
            .load(Uri.parse(uri))
            .placeholder(R.drawable.ic_photo_holder)
            .fit().centerCrop()
            .into(this)
    else setImageResource(R.drawable.ic_photo_holder)
}