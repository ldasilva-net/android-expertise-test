package net.ldasilva.expertisetest.ui.movie.list

import androidx.lifecycle.ViewModel
import net.ldasilva.expertisetest.data.SearchMovieType
import net.ldasilva.expertisetest.data.repository.MovieRepository

class MovieListViewModel(
        private val movieRepository: MovieRepository
) : ViewModel() {
    suspend fun getMovies(query: String, searchMovieType: SearchMovieType) {
        return movieRepository.getMovies(query, searchMovieType)
    }

    val movies = movieRepository.movies
}
