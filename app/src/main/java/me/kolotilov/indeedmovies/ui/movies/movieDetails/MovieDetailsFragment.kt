package me.kolotilov.indeedmovies.ui.movies.movieDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movie_details.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.base.BaseFragment
import me.kolotilov.indeedmovies.ui.common.logError
import me.kolotilov.indeedmovies.ui.common.toStringOrEmpty
import me.kolotilov.indeedmovies.ui.common.toVisibleOrGone
import me.kolotilov.indeedmovies.ui.common.withAll
import me.kolotilov.logic.MovieDetails

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, MovieDetailsViewModel.Factory>(MovieDetailsViewModel::class) {

    private val args by navArgs<MovieDetailsFragmentArgs>()
    private lateinit var currentMovie: MovieDetails

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.visibility = View.GONE
        if (args.isLocal)
            loadMovieFromDatabase()
        else
            loadMovieFromWeb()
        btn_save.setOnClickListener { onSaveClick() }
        btn_remove.setOnClickListener { onRemoveClick() }
    }

    //endregion

    private fun onSaveClick() {
        viewModel.saveMovie(currentMovie, iv_poster.drawToBitmap())
            .subscribe({
                btn_save.visibility = View.GONE
                btn_remove.visibility = View.VISIBLE
            }, ::logError)
            .disposeOnStop()
    }

    private fun onRemoveClick() {
        viewModel.removeMovie(currentMovie.id)
            .subscribe({
                btn_save.visibility = View.VISIBLE
                btn_remove.visibility = View.GONE
            }, ::logError)
            .disposeOnStop()
    }

    private fun loadMovieFromWeb() {
        // We can't merge Single with Maybe using default operators:
        viewModel.getMovieByIdFromWeb(args.movieId)
            .map { it to !viewModel.getMovieFromDatabase(args.movieId).isEmpty.blockingGet() }
            .subscribe({
                currentMovie = it.first
                fillView(it.first)
                if (!it.second)
                    btn_save.visibility = View.VISIBLE
                else
                    btn_remove.visibility = View.VISIBLE
            }, ::logError)
            .disposeOnStop()
    }

    private fun loadMovieFromDatabase() {
        viewModel.getMovieFromDatabase(args.movieId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currentMovie = it
                fillView(it)
                btn_remove.visibility = View.VISIBLE
            }, ::logError)
            .disposeOnStop()
    }

    private fun fillView(movie: MovieDetails) {
        requireView().visibility = View.VISIBLE
        movie.apply {
            tv_title.text = title
            loadPoster(movie)

            setTextOrHide(type, tv_type)
            setTextOrHide(genre, tv_genre, v_genre_delimiter)
            tv_duration.text = runtime.toStringOrEmpty()

            setTextOrHide(country, tv_country)
            setTextOrHide(year, tv_year, v_year_delimiter)

            setTextOrHide(director, tv_director, tv_director_hint)
            setTextOrHide(actors.toStringOrEmpty(), tv_actors, tv_actors_hint)

            setTextOrHide(metascore, tv_metascore, v_metascore_container, tv_metascore_hint)
            setTextOrHide(imdbRating?.let { getString(R.string.imdb_rating, imdbRating.toString()) }, tv_imdb_rating)
            setTextOrHide(imdbVotes, tv_imdb_votes, iv_star)
            tv_plot.text = plot
        }
    }

    private fun setTextOrHide(content: Any?, textView: TextView, vararg otherViews: View) {
        withAll(textView, *otherViews) {
            visibility = (content != null).toVisibleOrGone()
        }
        textView.text = content.toString()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun loadPoster(movie: MovieDetails) {
        iv_poster.apply {
            val posterUrl = movie.posterUrl
            if (posterUrl != null) {
                Glide.with(requireView())
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(this)
                setOnClickListener { onPosterClick(posterUrl, movie.title) }
            }
            else
                setImageDrawable(resources.getDrawable(R.drawable.ic_placeholder))
        }
    }

    private fun onPosterClick(posterUrl: String, title: String) {
        val action = MovieDetailsFragmentDirections.toPosterFragment(posterUrl, title)
        findNavController().navigate(action)
    }
}