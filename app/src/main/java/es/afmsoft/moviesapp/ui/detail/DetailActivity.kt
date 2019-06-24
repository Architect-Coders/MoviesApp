package es.afmsoft.moviesapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import es.afmsoft.data.repository.MoviesRepository
import es.afmsoft.data.repository.RegionRepository
import es.afmsoft.moviesapp.R
import es.afmsoft.moviesapp.app
import es.afmsoft.moviesapp.data.AndroidPermissionChecker
import es.afmsoft.moviesapp.data.GpsLocationDataSource
import es.afmsoft.moviesapp.data.database.RoomDataSource
import es.afmsoft.moviesapp.data.server.ServerDataSource
import es.afmsoft.moviesapp.getViewModel
import es.afmsoft.moviesapp.loadUrl
import es.afmsoft.moviesapp.ui.detail.DetailViewModel.UiModel
import es.afmsoft.moviesapp.ui.model.Movie
import es.afmsoft.usecases.GetMovie
import es.afmsoft.usecases.GetPopularMovies
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(intent.getIntExtra(MOVIE, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(movieDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.model.observe(this, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {
        when (model) {
            is UiModel.Content -> model.movie?.let {
                movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w1280/${it.posterPath}")
                movieDetailCollapsing.title = it.title

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val MOVIE = "MoviesRepository"
    }
}
