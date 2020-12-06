package ru.infoshell.contactsapp.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val name: String,
    val mainPhone: String,
    val photoUri: String?,
    val photoThumbnailUri: String?,
    val allPhones: List<String>
) : Parcelable