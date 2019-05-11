package net.ldasilva.expertisetest.data.repository

import androidx.lifecycle.LiveData
import net.ldasilva.expertisetest.data.SearchMovieType
import net.ldasilva.expertisetest.data.db.entity.MovieEntry

interface MovieRepository {
    suspend fun getMovie(id: Long): LiveData<MovieEntry>

    suspend fun getMovies(query: String, searchMovieType: SearchMovieType)

    val movies: LiveData<List<MovieEntry>>
}