package me.kolotilov.indeedmovies.ui.login

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.kolotilov.data.local.AuthorizationData
import me.kolotilov.data.local.LocalRepository
import me.kolotilov.indeedmovies.ui.base.BaseViewModel
import me.kolotilov.logic.QrContent
import me.kolotilov.logic.QrDecoder

class LoginViewModel(
    private val qrDecoder: QrDecoder,
    private val localRepository: LocalRepository
) : BaseViewModel() {

    fun isRegistered(): Single<Boolean> {
        return Single.fromCallable {
            localRepository.authorization.getAuthorizationData() != null
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun logIn(login: String, password: String): Completable {
        return Completable.fromRunnable {
            val data = localRepository.authorization.getAuthorizationData() ?: throw LoginFailedException()
            if (data.login != login || data.password != password)
                throw LoginFailedException()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun register(qrContent: String): Completable {
        return Completable.fromRunnable {
            val decoded = qrDecoder.decode(qrContent)
            localRepository.authorization.saveAuthorizationData(decoded.toAuthorizationData())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    class Factory(
        private val qrDecoder: QrDecoder,
        private val localRepository: LocalRepository
    ) : BaseViewModel.Factory<LoginViewModel> {

        override fun create(): LoginViewModel {
            return LoginViewModel(qrDecoder, localRepository)
        }
    }
}

private fun QrContent.toAuthorizationData(): AuthorizationData {
    val item = this
    return AuthorizationData(item.login, item.password)
}

class LoginFailedException : Exception()