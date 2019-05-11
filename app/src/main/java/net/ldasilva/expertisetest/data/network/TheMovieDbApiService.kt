package net.ldasilva.expertisetest.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import net.ldasilva.expertisetest.data.network.response.MoviesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "d81ffb57e028b3f225c8d62ec1f3cefa"
const val API_URL = "https://api.themoviedb.org/3/"
const val IMG_URL = "https://image.tmdb.org/t/p/w185"

interface TheMovieDbApiService {

    @GET("movie/popular")
    fun getMostPopular(): Deferred<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRated(): Deferred<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomings(): Deferred<MoviesResponse>

    @GET("search/movie")
    fun getMovies(
        @Query("query") query: String
    ): Deferred<MoviesResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): TheMovieDbApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDbApiService::class.java)
        }
    }
}