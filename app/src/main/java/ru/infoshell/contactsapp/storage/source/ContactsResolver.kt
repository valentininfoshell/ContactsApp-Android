package ru.infoshell.contactsapp.storage.source

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import ru.infoshell.contactsapp.data.source.ContactSource
import ru.infoshell.contactsapp.entities.Contact


class ContactsResolver(private val contentResolver: ContentResolver): ContactSource {

    override fun fetchContacts(): ArrayList<Contact> {
        val list = ArrayList<Contact>()

        val cursor: Cursor? = contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                CommonDataKinds.Phone.DISPLAY_NAME,
                CommonDataKinds.Phone.NUMBER,
                CommonDataKinds.Phone.PHOTO_URI,
                CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
                CommonDataKinds.Phone.TYPE
            ),
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            null,
            "${CommonDataKinds.Phone.DISPLAY_NAME} ASC"
        )

        cursor?.let {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val phoneIndex = cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER)
            val photoUriIndex = cursor.getColumnIndex(CommonDataKinds.Phone.PHOTO_URI)
            val thumbUriIndex = cursor.getColumnIndex(CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
            val phoneTypeIndex = cursor.getColumnIndex(CommonDataKinds.Phone.TYPE)

            var lastContactName: String? = null

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                val phone = formatNumber(cursor.getString(phoneIndex))
                val phoneType = cursor.getInt(phoneTypeIndex)

                if (name != lastContactName) {
                    lastContactName = name

                    list.add(
                        Contact(
                            name = name,
                            mainPhone = phone,
                            photoUri = cursor.getString(photoUriIndex),
                            photoThumbnailUri = cursor.getString(thumbUriIndex),
                            allPhones = listOf(phone),
                        )
                    )
                } else {
                    val oldContact = list[list.lastIndex]
                    val allPhones = LinkedHashSet(oldContact.allPhones)
                    allPhones.add(phone)

                    list[list.lastIndex] = Contact(
                        name = oldContact.name,
                        mainPhone = if (phoneType == CommonDataKinds.Phone.TYPE_MAIN) phone else oldContact.mainPhone,
                        photoUri = oldContact.photoUri,
                        photoThumbnailUri = oldContact.photoThumbnailUri,
                        allPhones = allPhones.toList(),
                    )
                }
            }
            cursor.close()
        }

        return list
    }

    fun formatNumber(originalNumber: String): String {
        return originalNumber.replace("[^\\d+]".toRegex(), "")
    }
}