package ru.infoshell.contactsapp.presentation.di

import org.koin.dsl.module
import ru.infoshell.contactsapp.presentation.ui.contacts.detail.PhonesAdapter
import ru.infoshell.contactsapp.presentation.ui.contacts.list.ContactsAdapter

val adapterModule = module {
    factory { ContactsAdapter() }
    factory { PhonesAdapter() }
}