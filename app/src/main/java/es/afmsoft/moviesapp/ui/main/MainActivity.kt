package es.afmsoft.moviesapp.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import es.afmsoft.moviesapp.PermissionRequester
import es.afmsoft.moviesapp.R
import es.afmsoft.moviesapp.startActivity
import es.afmsoft.moviesapp.ui.detail.DetailActivity
import es.afmsoft.moviesapp.ui.main.MoviesListViewModel.UiModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val adapter = MoviesAdapter {
        viewModel.onMovieClicked(it)
    }

    private val viewModel: MoviesListViewModel by currentScope.viewModel(this)
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesGrid.layoutManager = GridLayoutManager(this, MOVIES_PER_ROW)
        moviesGrid.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        progressBar.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> startActivity<DetailActivity> { putExtra(DetailActivity.MOVIE, model.movie.id) }
            is UiModel.RequestLocationPermission ->
                coarsePermissionRequester.request { viewModel.onCoarsePermissionRequested() }
        }
    }

    companion object {
        const val MOVIES_PER_ROW = 3
    }
}
