package me.kolotilov.indeedmovies.ui.movies.savedMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_recycler.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.base.BaseFragment
import me.kolotilov.indeedmovies.ui.common.castTo
import me.kolotilov.indeedmovies.ui.common.logError
import me.kolotilov.indeedmovies.ui.movies.MoviesFragmentDirections
import me.kolotilov.indeedmovies.ui.movies.movieList.MovieListAdapter
import me.kolotilov.indeedmovies.ui.movies.movieList.MovieListItemDelegate
import me.kolotilov.logic.MovieSearchItem
import me.kolotilov.logic.toMovieSearchItem

class SavedMoviesFragment : BaseFragment<SavedMoviesViewModel, SavedMoviesViewModel.Factory>(SavedMoviesViewModel::class) {

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = MovieListAdapter(MovieClickDelegateImpl())
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    //endregion

    private fun loadData() {
        viewModel.getAllMovies()
            .subscribe({
                recycler.adapter!!.castTo<MovieListAdapter>().submitList(it.map { it.toMovieSearchItem() })
            }, ::logError)
            .disposeOnStop()
    }

    private inner class MovieClickDelegateImpl : MovieListItemDelegate {

        override fun redirectToPosterView(
            item: MovieSearchItem,
            tvTitle: TextView,
            ivPoster: ImageView
        ) {
            val action = MoviesFragmentDirections.toDetailsFragment(item.id, true)
            findNavController().navigate(action)
        }

        override fun redirectToDetailsView(id: String) {
            val action = MoviesFragmentDirections.toDetailsFragment(id, true)
            findNavController().navigate(action)
        }
    }
}