package me.kolotilov.indeedmovies.ui.movies.movieList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_list.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.base.BaseFragment
import me.kolotilov.indeedmovies.ui.common.castTo
import me.kolotilov.indeedmovies.ui.common.logError
import me.kolotilov.indeedmovies.ui.movies.MoviesFragmentDirections
import me.kolotilov.logic.MovieSearchItem

class MovieListFragment : BaseFragment<MovieListViewModel, MovieListViewModel.Factory>(MovieListViewModel::class) {

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = MovieListAdapter(MovieListItemDelegateImpl())
        et_search.addTextChangedListener(OnQueryTextListener())
        subscribeToSearchResults()
    }

    //endregion


    private fun subscribeToSearchResults() {
        viewModel.searchResults()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                recycler.adapter!!.castTo<MovieListAdapter>().submitList(it)
            }, ::logError)
            .disposeOnStop()
    }

    private inner class OnQueryTextListener : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            viewModel.search(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private inner class MovieListItemDelegateImpl : MovieListItemDelegate {

        override fun redirectToPosterView(item: MovieSearchItem, tvTitle: TextView, ivPoster: ImageView) {
            val posterUrl = item.posterUrl!!
            val action = MoviesFragmentDirections.toPosterFragment(posterUrl, item.title)
            findNavController().navigate(action)
        }

        override fun redirectToDetailsView(id: String) {
            val action = MoviesFragmentDirections.toDetailsFragment(id, false)
            findNavController().navigate(action)
        }
    }
}