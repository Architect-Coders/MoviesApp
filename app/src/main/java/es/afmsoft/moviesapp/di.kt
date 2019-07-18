package es.afmsoft.moviesapp

import android.app.Application
import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.data.repository.PermissionChecker
import es.afmsoft.data.repository.RegionRepository
import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.data.sources.LocationDataSource
import es.afmsoft.data.sources.RemoteDataSource
import es.afmsoft.moviesapp.data.AndroidPermissionChecker
import es.afmsoft.moviesapp.data.GpsLocationDataSource
import es.afmsoft.moviesapp.data.database.MovieDatabase
import es.afmsoft.moviesapp.data.database.RoomDataSource
import es.afmsoft.moviesapp.data.server.ServerDataSource
import es.afmsoft.moviesapp.ui.detail.DetailActivity
import es.afmsoft.moviesapp.ui.detail.DetailViewModel
import es.afmsoft.moviesapp.ui.main.MainActivity
import es.afmsoft.moviesapp.ui.main.MoviesListViewModel
import es.afmsoft.usecases.GetMovie
import es.afmsoft.usecases.GetPopularMovies
import es.afmsoft.usecases.ToggleFavouriteMovie
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { ServerDataSource() }
    factory<LocationDataSource> { GpsLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(named("apiKey")), get()) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MoviesListViewModel(get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped {  GetMovie(get()) }
        scoped {  ToggleFavouriteMovie(get()) }
    }
}
