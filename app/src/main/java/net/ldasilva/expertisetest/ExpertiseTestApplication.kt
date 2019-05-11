package net.ldasilva.expertisetest

import android.app.Application
import net.ldasilva.expertisetest.data.db.ExpertiseTestDatabase
import net.ldasilva.expertisetest.data.network.*
import net.ldasilva.expertisetest.data.repository.MovieRepository
import net.ldasilva.expertisetest.data.repository.MovieRepositoryImpl
import net.ldasilva.expertisetest.ui.movie.detail.MovieDetailViewModelFactory
import net.ldasilva.expertisetest.ui.movie.list.MovieListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class ExpertiseTestApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ExpertiseTestApplication))
        bind() from singleton { ExpertiseTestDatabase(instance()) }
        bind() from singleton { instance<ExpertiseTestDatabase>().movieDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TheMovieDbApiService(instance()) }
        bind<MovieDataSource>() with singleton { MovieDataSourceImpl(instance()) }
        bind<MovieRepository>() with singleton { MovieRepositoryImpl(instance(), instance()) }
        bind() from provider { MovieListViewModelFactory(instance()) }
        bind() from factory { id: Long -> MovieDetailViewModelFactory(id, instance()) }
    }
}