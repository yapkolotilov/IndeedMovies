package me.kolotilov.data.local.keyStore

import android.content.Context
import de.adorsys.android.securestoragelibrary.SecurePreferences

/**
 * Interactor for storing data safely.
 */
internal interface KeyStoreInteractor {

    fun getString(key: String): String?

    fun setString(key: String, value: String)
}

internal class KeyStoreInteractorImpl(
    private val context: Context
) : KeyStoreInteractor {

    override fun getString(key: String): String? {
        return SecurePreferences.getStringValue(context, key, null)

    }

    override fun setString(key: String, value: String) {
        SecurePreferences.setValue(context, key, value)
    }
}
