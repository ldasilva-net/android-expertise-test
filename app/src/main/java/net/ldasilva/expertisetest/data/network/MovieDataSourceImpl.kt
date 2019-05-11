package net.ldasilva.expertisetest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ldasilva.expertisetest.data.SearchMovieType
import net.ldasilva.expertisetest.data.db.entity.MovieEntry
import net.ldasilva.expertisetest.data.network.response.MoviesResponse
import net.ldasilva.expertisetest.internal.NoConnectivityException

class MovieDataSourceImpl(
    private val theMovieDbApiService: TheMovieDbApiService
) : MovieDataSource {
    private val _downloadedMovies= MutableLiveData<MoviesResponse>()
    override val downloadedMovies: LiveData<MoviesResponse>
        get() = _downloadedMovies

    override suspend fun fetchMovies(query: String, searchMovieType: SearchMovieType) {
        try {
            val fetchedMovies = when(searchMovieType) {
                SearchMovieType.MOST_POPULAR -> theMovieDbApiService.getMostPopular().await()
                SearchMovieType.TOP_RATED -> theMovieDbApiService.getTopRated().await()
                SearchMovieType.UPCOMING -> theMovieDbApiService.getUpcomings().await()
                SearchMovieType.FILTER -> theMovieDbApiService.getMovies(query).await()
            }

            _downloadedMovies.postValue(fetchedMovies)
        }
        catch (e: NoConnectivityException) {
            _downloadedMovies.postValue(MoviesResponse(listOf<MovieEntry>()))
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}