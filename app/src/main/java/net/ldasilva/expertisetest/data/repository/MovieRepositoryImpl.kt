package net.ldasilva.expertisetest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ldasilva.expertisetest.data.SearchMovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.ldasilva.expertisetest.data.db.MovieDao
import net.ldasilva.expertisetest.data.db.entity.MovieEntry
import net.ldasilva.expertisetest.data.network.MovieDataSource
import net.ldasilva.expertisetest.data.network.response.MoviesResponse

class MovieRepositoryImpl(
        private val movieDao: MovieDao,
        private val movieDataSource: MovieDataSource
    ) : MovieRepository {

    private var _movies_cache: List<MovieEntry>
    private val _movies = MutableLiveData<List<MovieEntry>>()
    override val movies: LiveData<List<MovieEntry>>
        get() = _movies

    init {
        _movies_cache = listOf()

        movieDataSource.apply {
            downloadedMovies.observeForever { result ->
                persistFetchedMovies(result)

                var finalList = if (result.movies.count() > 0) result.movies else _movies_cache

                _movies.postValue(finalList)
            }
        }
    }

    override suspend fun getMovie(id: Long): LiveData<MovieEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext movieDao.getMovie(id)
        }
    }

    override suspend fun getMovies(query: String, searchMovieType: SearchMovieType) {
        return withContext(Dispatchers.IO) {
            movieDataSource.fetchMovies(query, searchMovieType)

            val result = when(searchMovieType) {
                SearchMovieType.MOST_POPULAR -> movieDao.getMostPopular()
                SearchMovieType.TOP_RATED -> movieDao.getTopRated()
                SearchMovieType.UPCOMING -> movieDao.getUpcomings()
                SearchMovieType.FILTER -> movieDao.getMovies(query)
            }

            _movies_cache = result
        }
    }

    private fun persistFetchedMovies(response: MoviesResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val movieList = response.movies
            movieDao.upsert(movieList)
        }
    }
}