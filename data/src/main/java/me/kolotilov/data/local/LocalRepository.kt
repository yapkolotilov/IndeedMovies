package me.kolotilov.data.local

import android.content.Context
import me.kolotilov.data.local.database.MoviesRepository
import me.kolotilov.data.local.database.MoviesRepositoryImpl
import me.kolotilov.data.local.keyStore.KeyStoreInteractorImpl

interface LocalRepository {

    val authorization: AuthorizationRepository

    val movies: MoviesRepository
}

class LocalRepositoryImpl(
    context: Context
): LocalRepository {

    private val keyStoreInteractor = KeyStoreInteractorImpl(context)

    override val authorization: AuthorizationRepository = AuthorizationRepositoryImpl(keyStoreInteractor)

    override val movies: MoviesRepository = MoviesRepositoryImpl(context)
}