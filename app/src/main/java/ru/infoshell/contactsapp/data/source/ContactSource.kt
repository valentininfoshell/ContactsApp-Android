package ru.infoshell.contactsapp.data.source

import ru.infoshell.contactsapp.entities.Contact

interface ContactSource {

    fun fetchContacts(): ArrayList<Contact>
}