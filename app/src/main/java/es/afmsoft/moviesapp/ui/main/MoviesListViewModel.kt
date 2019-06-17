package es.afmsoft.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.afmsoft.moviesapp.MoviesApp
import es.afmsoft.moviesapp.model.MoviesRepository
import es.afmsoft.moviesapp.Scope
import es.afmsoft.moviesapp.ui.model.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(private val moviesRepository :MoviesRepository) : ViewModel(),
    Scope by Scope.Impl {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
    get() {
        if (_model.value == null) refresh()
        return _model
    }

    private fun refresh() {

        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            val movies = moviesRepository.discoverMovies(2019, "es")
            _model.value = UiModel.Content(movies)
        }
    }

    init {
        initScope()
    }

    override fun onCleared() {
        closeScope()
        super.onCleared()
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    sealed class UiModel {
        object Loading : UiModel()
        object RequestLocationPermission : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
    }
}
