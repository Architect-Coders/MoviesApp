package es.afmsoft.moviesapp

import es.afmsoft.data.repository.PermissionChecker
import es.afmsoft.data.repository.RegionRepository
import es.afmsoft.data.sources.LocalDataSource
import es.afmsoft.data.sources.LocationDataSource
import es.afmsoft.data.sources.RemoteDataSource
import es.afmsoft.domain.Movie
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mockedMovie = Movie(
    0,
    0,
    "Title",
    "",
    "",
    false,
    "Overview"
)

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeMovies = listOf(
    mockedMovie.copy(1),
    mockedMovie.copy(2),
    mockedMovie.copy(3),
    mockedMovie.copy(4)
)

class FakeLocalDataSource : LocalDataSource {

    var movies = emptyList<Movie>()

    override suspend fun isEmpty() = movies.isEmpty()

    override suspend fun getPopularMovies() = movies

    override suspend fun saveMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override suspend fun getMovie(movieId: Int) = movies.first { it.id == movieId }

    override suspend fun update(movie: Movie) {
        this.movies = movies.filterNot { it.id == movie.id } + movie
    }
}

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun getPopularMovies(apiKey: String, regionRepository: RegionRepository) = movies

    var movies = defaultFakeMovies
}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String? = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}
