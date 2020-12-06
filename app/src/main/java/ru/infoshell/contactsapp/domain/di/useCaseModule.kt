package ru.infoshell.contactsapp.domain.di

import org.koin.dsl.module
import ru.infoshell.contactsapp.domain.usecase.ContactsListUseCase

val useCaseModule = module {

    single { ContactsListUseCase(get()) }
}