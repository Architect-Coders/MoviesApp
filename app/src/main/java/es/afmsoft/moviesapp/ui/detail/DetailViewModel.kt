package es.afmsoft.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private fun refresh() =
    launch {
        _model.value = UiModel(getMovie.invoke(movieId))
    }

    fun onToggleFavouriteMovieClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(toggleFavouriteMovie.invoke(it))
        }
    }

    class UiModel(val movie: Movie?)
}
