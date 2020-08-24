package me.kolotilov.indeedmovies.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Base class for all fragments.
 *
 * @param VM ViewModel class.
 * @param F Fragment class.
 * @param modelClass ViewModel class.
 */
abstract class BaseFragment<VM : BaseViewModel, F : BaseViewModel.Factory<VM>>(
    private val modelClass: KClass<VM>
) : DaggerFragment() {

    /**
     * ViewModel.
     */
    protected val viewModel get() = _model

    @Inject
    lateinit var factory: F
    private lateinit var _model: VM
    private val _onStopDisposable = ReusableDisposable()

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _model = ViewModelProvider(this, factory).get(modelClass.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _onStopDisposable.reInitialize()
    }

    override fun onStop() {
        super.onStop()
        _onStopDisposable.dispose()
    }

    //endregion


    /**
     * Disposes Disposable on OnStop call.
     */
    protected fun Disposable.disposeOnStop() {
        _onStopDisposable.add(this)
    }
}

private class ReusableDisposable {

    private var _disposable = CompositeDisposable()

    fun add(disposable: Disposable) {
        _disposable.add(disposable)
    }

    fun dispose() {
        _disposable.dispose()
    }

    fun reInitialize() {
        if (_disposable.isDisposed)
            _disposable = CompositeDisposable()
    }
}

/**
 * Show message.
 *
 * @param message Message.
 */
fun Fragment.showMessage(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}