package net.ldasilva.expertisetest.data.network

import androidx.lifecycle.LiveData
import net.ldasilva.expertisetest.data.SearchMovieType
import net.ldasilva.expertisetest.data.network.response.MoviesResponse

interface MovieDataSource {
    val downloadedMovies: LiveData<MoviesResponse>

    suspend fun fetchMovies(query: String = "", searchMovieType: SearchMovieType)
}