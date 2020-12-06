package ru.infoshell.contactsapp.storage.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.infoshell.contactsapp.data.source.ContactSource
import ru.infoshell.contactsapp.storage.source.ContactsResolver

val storageModule = module {

    factory { ContactsResolver(get()) as ContactSource }

    single { androidApplication().contentResolver }
}