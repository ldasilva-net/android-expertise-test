package net.ldasilva.expertisetest.ui.movie.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.movie_detail_fragment.*
import net.ldasilva.expertisetest.R
import net.ldasilva.expertisetest.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.ldasilva.expertisetest.data.db.entity.MovieEntry
import net.ldasilva.expertisetest.data.network.IMG_URL
import net.ldasilva.expertisetest.internal.IdNotFoundException
import net.ldasilva.expertisetest.internal.glide.GlideApp
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory


class MovieDetailFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory
            : ((Long) -> MovieDetailViewModelFactory) by factory()

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }
        val id = safeArgs?.id ?: throw IdNotFoundException()

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(id))
            .get(MovieDetailViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val movie = viewModel.movie.await()

        movie.observe(this@MovieDetailFragment, Observer {
            if (it == null) return@Observer

            updateTitle(it.title)
            updateTextViews(it)
            updatePoster(it.posterPath)
        })
    }

    private fun updateTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    private fun updateTextViews(movie: MovieEntry) {
        textView_release_date_lbl.text = "Release date"
        textView_release_date.text = movie.releaseDate

        textView_average_lbl.text = "Average"
        textView_average.text = movie.voteAverage.toString()

        textView_popularity_lbl.text = "Popularity"
        textView_popularity.text = movie.popularity.toString()

        textView_details.text = movie.overview
    }

    private fun updatePoster(poster: String?) {
        imageView_poster_detail.setImageResource(R.drawable.ic_no_poster)

        if (poster == null)
            return

        GlideApp.with(this@MovieDetailFragment)
            .load(IMG_URL + poster)
            .into(imageView_poster_detail)
    }
}
