package ru.infoshell.contactsapp.data.di

import org.koin.dsl.module
import ru.infoshell.contactsapp.data.repository.ContactsRepositoryImpl
import ru.infoshell.contactsapp.domain.repository.ContactsRepository

val repositoryModule = module {

    single { ContactsRepositoryImpl(get()) as ContactsRepository }
}