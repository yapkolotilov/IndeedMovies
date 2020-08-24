package me.kolotilov.indeedmovies.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.Disposable

/**
 * Base class for all ViewModels.
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Denotes that other subscriber handles UI interaction.
     */
    protected fun Disposable.ignoreDisposure() {}

    /**
     * Base factory for ViewModels.
     */
    interface Factory<VM : BaseViewModel> : ViewModelProvider.Factory {

        fun create(): VM

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return create() as T
        }
    }
}