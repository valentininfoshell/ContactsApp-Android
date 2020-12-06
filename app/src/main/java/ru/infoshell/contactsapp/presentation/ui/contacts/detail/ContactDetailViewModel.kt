package ru.infoshell.contactsapp.presentation.ui.contacts.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.entities.Contact
import ru.infoshell.contactsapp.presentation.ui.base.BaseViewModel

class ContactDetailViewModel(application: Application) : BaseViewModel(application) {

    val contactLiveData = MutableLiveData<Contact>()

    fun setContact(contact: Contact?) {
        contact?.let {
            isError.set(false)
            contactLiveData.value = it
        } ?: showError(R.string.unknown_error)
    }
}