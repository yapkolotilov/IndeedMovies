package me.kolotilov.indeedmovies.ui.movies.movieList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_login.view.tv_title
import kotlinx.android.synthetic.main.movie_list_item.view.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.common.toStringOrEmpty
import me.kolotilov.logic.MovieSearchItem

class MovieListAdapter(
    private val clickDelegate: MovieListItemDelegate
) : ListAdapter<MovieSearchItem, MovieListAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.tv_title
        private val ivPoster = view.iv_poster
        private val tvYear = view.tv_year
        private val tvType = view.tv_type

        private lateinit var currentItem: MovieSearchItem

        init {
            itemView.setOnClickListener {
                clickDelegate.redirectToDetailsView(currentItem.id)
            }

            ivPoster.setOnClickListener {
                if (currentItem.posterUrl != null) {
                    clickDelegate.redirectToPosterView(currentItem, tvTitle, ivPoster)
                }
            }
        }


        fun bind(item: MovieSearchItem) {
            currentItem = item
            tvTitle.text = item.title
            tvYear.text = item.year.toStringOrEmpty()
            tvType.text = item.type.toStringOrEmpty()
            initializeIvPoster(item)
        }

        @Suppress("DEPRECATION")
        @SuppressLint("UseCompatLoadingForDrawables")
        private fun initializeIvPoster(item: MovieSearchItem) {
            if (item.posterUrl != null)
                Glide.with(itemView)
                    .load(item.posterUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(ivPoster)
            else ivPoster.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_placeholder))
        }
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<MovieSearchItem>() {

        override fun areItemsTheSame(oldItem: MovieSearchItem, newItem: MovieSearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieSearchItem, newItem: MovieSearchItem): Boolean {
            return oldItem == newItem
        }
    }
}

interface MovieListItemDelegate {

    fun redirectToPosterView(item: MovieSearchItem, tvTitle: TextView, ivPoster: ImageView)

    fun redirectToDetailsView(id: String)
}