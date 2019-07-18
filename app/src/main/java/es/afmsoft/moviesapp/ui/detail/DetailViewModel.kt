package es.afmsoft.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
<<<<<<< HEAD
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.ScopedViewModel
import es.afmsoft.usecases.GetMovie
import es.afmsoft.usecases.ToggleFavouriteMovie
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val getMovie: GetMovie,
    private val toggleFavouriteMovie: ToggleFavouriteMovie
) : ScopedViewModel() {
=======
import androidx.lifecycle.ViewModel
import es.afmsoft.domain.Movie
import es.afmsoft.moviesapp.Scope
import es.afmsoft.usecases.GetMovie
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int, private val getMovie: GetMovie) :
    ViewModel(), Scope by Scope.Impl {
>>>>>>> master

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private fun refresh() =
    launch {
<<<<<<< HEAD
        _model.value = UiModel(getMovie.invoke(movieId))
    }

    fun onToggleFavouriteMovieClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(toggleFavouriteMovie.invoke(it))
        }
    }

    class UiModel(val movie: Movie?)
=======
        _model.value = UiModel.Loading
        _model.value = UiModel.Content(getMovie.invoke(movieId))
    }

    init {
        initScope()
    }

    override fun onCleared() {
        closeScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movie: Movie?) : UiModel()
    }
>>>>>>> master
}
