package ru.infoshell.contactsapp.data.repository

import io.reactivex.Single
import ru.infoshell.contactsapp.data.source.ContactSource
import ru.infoshell.contactsapp.domain.repository.ContactsRepository
import ru.infoshell.contactsapp.entities.Contact

class ContactsRepositoryImpl(private val contactsResolver: ContactSource) : ContactsRepository {

    override fun getContactList(): Single<List<Contact>> {
        return Single.fromCallable { contactsResolver.fetchContacts() }
    }
}