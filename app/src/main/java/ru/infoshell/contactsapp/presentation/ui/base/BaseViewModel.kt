package ru.infoshell.contactsapp.presentation.ui.base

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val errorMessage = ObservableField("")
    val isError = ObservableBoolean(false)

    protected val compositeDisposable = CompositeDisposable()


    protected fun getContext(): Context {
        return getApplication<Application>()
    }

    protected fun getString(@StringRes resId: Int): String {
        return getContext().getString(resId)
    }

    protected fun showError(@StringRes messageResId: Int) {
        showError(getString(messageResId))
    }

    protected fun showError(message: String) {
        isError.set(true)
        errorMessage.set(message)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}