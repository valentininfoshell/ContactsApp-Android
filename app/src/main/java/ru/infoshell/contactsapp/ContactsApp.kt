package ru.infoshell.contactsapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.infoshell.contactsapp.data.di.repositoryModule
import ru.infoshell.contactsapp.domain.di.useCaseModule
import ru.infoshell.contactsapp.presentation.di.adapterModule
import ru.infoshell.contactsapp.presentation.di.viewModelModule
import ru.infoshell.contactsapp.storage.di.storageModule

class ContactsApp : Application() {

    private val appModules by lazy {
        listOf(
            repositoryModule,
            useCaseModule,
            viewModelModule,
            storageModule,
            adapterModule
        )
    }

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ContactsApp)
            modules(appModules)
        }
    }
}