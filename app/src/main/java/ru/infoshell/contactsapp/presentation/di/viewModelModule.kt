package ru.infoshell.contactsapp.presentation.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.infoshell.contactsapp.presentation.ui.contacts.detail.ContactDetailViewModel
import ru.infoshell.contactsapp.presentation.ui.contacts.list.ContactListViewModel

val viewModelModule = module {

    viewModel { ContactListViewModel(get(), androidApplication()) }
    viewModel { ContactDetailViewModel(androidApplication()) }
}