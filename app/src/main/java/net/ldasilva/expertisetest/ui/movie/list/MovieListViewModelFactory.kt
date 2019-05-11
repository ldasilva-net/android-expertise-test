package net.ldasilva.expertisetest.ui.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.ldasilva.expertisetest.data.repository.MovieRepository

class MovieListViewModelFactory(
        private val movieRepository: MovieRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieListViewModel(
                movieRepository
        ) as T
    }
}