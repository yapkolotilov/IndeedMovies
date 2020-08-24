package me.kolotilov.data.local

import android.content.Context
import me.kolotilov.data.local.database.MoviesRepository
import me.kolotilov.data.local.database.MoviesRepositoryImpl
import me.kolotilov.data.local.fileSystem.ImageInteractor
import me.kolotilov.data.local.fileSystem.ImageInteractorImpl
import me.kolotilov.data.local.keyStore.KeyStoreInteractorImpl

interface LocalRepository {

    val authorization: AuthorizationRepository

    val movies: MoviesRepository

    val images: ImageInteractor
}

class LocalRepositoryImpl(
    context: Context
): LocalRepository {

    private val keyStoreInteractor = KeyStoreInteractorImpl(context)

    override val authorization: AuthorizationRepository = AuthorizationRepositoryImpl(keyStoreInteractor)

    override val movies: MoviesRepository = MoviesRepositoryImpl(context)

    override val images: ImageInteractor = ImageInteractorImpl(context)
}