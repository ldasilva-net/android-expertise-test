package net.ldasilva.expertisetest.ui.movie.detail

import androidx.lifecycle.ViewModel
import net.ldasilva.expertisetest.data.repository.MovieRepository
import net.ldasilva.expertisetest.internal.lazyDeferred

class MovieDetailViewModel(
        private val id: Long,
        private val movieRepository: MovieRepository
) : ViewModel() {

    val movie by lazyDeferred {
        movieRepository.getMovie(id)
    }
}
