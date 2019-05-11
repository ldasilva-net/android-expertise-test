package net.ldasilva.expertisetest.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.ldasilva.expertisetest.data.repository.MovieRepository

class MovieDetailViewModelFactory(
        private val id: Long,
        private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(id, movieRepository) as T
    }
}