package ru.infoshell.contactsapp.domain.usecase

import io.reactivex.Single
import ru.infoshell.contactsapp.domain.repository.ContactsRepository
import ru.infoshell.contactsapp.entities.Contact

class ContactsListUseCase(private val contactsRepository: ContactsRepository) {

    fun getContactsList(): Single<List<Contact>> {
        return contactsRepository.getContactList()
    }
}