package me.kolotilov.indeedmovies.ui.movies.movieList

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import me.kolotilov.data.web.WebRepository
import me.kolotilov.indeedmovies.ui.base.BaseViewModel
import me.kolotilov.indeedmovies.ui.common.logError
import me.kolotilov.logic.MovieSearchItem

class MovieListViewModel(
    private val webRepository: WebRepository
) : BaseViewModel() {

    private val searchSubject = BehaviorSubject.create<List<MovieSearchItem>>()

    fun searchResults(): Observable<List<MovieSearchItem>> {
        return searchSubject
    }

    fun search(query: String) {
        if (query.endsWith(" "))
            return
        webRepository.movies.searchMovies(query, 1)
            .subscribe({
                searchSubject.onNext(it)
            }, ::logError)
            .ignoreDisposure()
    }


    class Factory(
        private val webRepository: WebRepository
    ) : BaseViewModel.Factory<MovieListViewModel> {

        override fun create(): MovieListViewModel {
            return MovieListViewModel(webRepository)
        }
    }
}