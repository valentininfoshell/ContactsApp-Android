package ru.infoshell.contactsapp.presentation.ui.contacts.list

import android.app.Application
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.data.ext.inBackground
import ru.infoshell.contactsapp.domain.usecase.ContactsListUseCase
import ru.infoshell.contactsapp.entities.Contact
import ru.infoshell.contactsapp.presentation.ui.base.BaseViewModel

class ContactListViewModel(
    private val contactsListUseCase: ContactsListUseCase,
    application: Application
) : BaseViewModel(application) {

    val contactListLiveData = MutableLiveData<List<Contact>>()

    val isLoaded = ObservableBoolean(false)

    fun getContactList() {
        compositeDisposable.add(
            contactsListUseCase.getContactsList()
                .doOnSubscribe { isLoaded.set(false) }
                .inBackground()
                .doFinally { isLoaded.set(true) }
                .subscribe({ list ->
                    if (list.isNullOrEmpty()) {
                        showError(R.string.contact_permission_denied)
                    } else {
                        isError.set(false)
                        contactListLiveData.value = list
                    }
                }, { error ->
                    showError(R.string.unknown_error)
                })
        )
    }

    fun onPermissionDenied() {
        showError(R.string.contact_permission_denied)
    }
}