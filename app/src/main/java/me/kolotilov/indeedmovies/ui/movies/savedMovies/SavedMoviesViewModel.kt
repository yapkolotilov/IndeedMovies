package me.kolotilov.indeedmovies.ui.movies.savedMovies

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import me.kolotilov.data.local.LocalRepository
import me.kolotilov.indeedmovies.ui.base.BaseViewModel
import me.kolotilov.logic.MovieDetails

class SavedMoviesViewModel(
    private val localRepository: LocalRepository
) : BaseViewModel() {

    fun getAllMovies(): Single<List<MovieDetails>> {
        return localRepository.movies.getAllMovies()
            .observeOn(AndroidSchedulers.mainThread())
    }

    class Factory(
        private val localRepository: LocalRepository
    ) : BaseViewModel.Factory<SavedMoviesViewModel> {

        override fun create(): SavedMoviesViewModel {
            return SavedMoviesViewModel(localRepository)
        }
    }
}