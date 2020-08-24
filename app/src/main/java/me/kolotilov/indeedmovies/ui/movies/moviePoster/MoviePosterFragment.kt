package me.kolotilov.indeedmovies.ui.movies.moviePoster

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_poster.*
import me.kolotilov.indeedmovies.R

class MoviePosterFragment : Fragment() {

    private val args: MoviePosterFragmentArgs by navArgs()

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_poster.apply {
            Glide.with(view)
                .load(args.posterUrl)
                .into(this)
        }
        tv_title.apply {
            text = args.movieName
        }
        btn_close.setOnClickListener { findNavController().navigateUp() }
    }

    //endregion
}