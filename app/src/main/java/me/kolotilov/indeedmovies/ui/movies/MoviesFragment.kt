package me.kolotilov.indeedmovies.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.movies.movieList.MovieListFragment
import me.kolotilov.indeedmovies.ui.movies.savedMovies.SavedMoviesFragment

class MoviesFragment : Fragment() {

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.adapter = MoviesPagerAdapter(childFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    //endregion


    private inner class MoviesPagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val pages = listOf(
            getString(R.string.search) to MovieListFragment(),
            "Saved" to SavedMoviesFragment()
        )

        override fun getCount(): Int {
            return pages.size
        }

        override fun getItem(position: Int): Fragment {
            return pages[position].second
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return pages[position].first
        }
    }
}