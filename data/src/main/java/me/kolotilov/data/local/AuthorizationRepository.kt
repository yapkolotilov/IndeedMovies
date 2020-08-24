package me.kolotilov.data.local

import me.kolotilov.data.local.keyStore.KeyStoreInteractor

/**
 * Interactor for storing data safely.
 */
interface AuthorizationRepository {

    fun saveAuthorizationData(data: AuthorizationData)

    fun getAuthorizationData(): AuthorizationData?
}

internal class AuthorizationRepositoryImpl(
    private val keyStoreInteractor: KeyStoreInteractor
) : AuthorizationRepository {

    private companion object {
        const val LOGIN_KEY = "Login"
        const val PASSWORD_KEY = "Password"
    }

    override fun getAuthorizationData(): AuthorizationData? {
        val login = keyStoreInteractor.getString(LOGIN_KEY)
        val password = keyStoreInteractor.getString(PASSWORD_KEY)
        return if (login == null || password == null)
            null
        else
            AuthorizationData(login, password)
    }

    override fun saveAuthorizationData(data: AuthorizationData) {
        keyStoreInteractor.setString(LOGIN_KEY, data.login)
        keyStoreInteractor.setString(PASSWORD_KEY, data.password)
    }
}

data class AuthorizationData(
    val login: String,
    val password: String
)