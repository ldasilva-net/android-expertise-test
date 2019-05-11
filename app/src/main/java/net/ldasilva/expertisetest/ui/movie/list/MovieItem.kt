package net.ldasilva.expertisetest.ui.movie.list

import net.ldasilva.expertisetest.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie.*
import net.ldasilva.expertisetest.data.db.entity.MovieEntry
import net.ldasilva.expertisetest.data.network.IMG_URL
import net.ldasilva.expertisetest.internal.glide.GlideApp

class MovieItem(
    val movieEntry: MovieEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            updateTextViews()
            updatePoster()
        }
    }

    override fun getLayout() = R.layout.item_movie

    private fun ViewHolder.updateTextViews() {
        textView_title.text = movieEntry.title
        textView_release_date.text = "Release date: ${movieEntry.releaseDate}"
        textView_details.text = movieEntry.overview
    }

    private fun ViewHolder.updatePoster() {
        imageView_poster.setImageResource(R.drawable.ic_no_poster)

        if (movieEntry.posterPath == null)
            return

        GlideApp.with(this.containerView)
            .load(IMG_URL + movieEntry.posterPath)
            .into(imageView_poster)
    }
}