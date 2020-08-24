package me.kolotilov.indeedmovies.ui.common

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_placeholder.view.*
import me.kolotilov.indeedmovies.R

class PlaceholderAdapter(
    private val icon: Drawable,
    private val text: String
) : RecyclerView.Adapter<PlaceholderAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_placeholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivIcon = view.iv_icon
        private val tvPrompt = view.tv_prompt

        fun bind() {
            ivIcon.setImageDrawable(icon)
            tvPrompt.text = text
        }
    }
}