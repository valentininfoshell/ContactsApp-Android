package ru.infoshell.contactsapp.domain.repository

import io.reactivex.Single
import ru.infoshell.contactsapp.entities.Contact

interface ContactsRepository {

    fun getContactList(): Single<List<Contact>>
}