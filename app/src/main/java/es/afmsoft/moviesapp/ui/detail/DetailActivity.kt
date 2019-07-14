package es.afmsoft.moviesapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import es.afmsoft.moviesapp.R
import es.afmsoft.moviesapp.loadUrl
import es.afmsoft.moviesapp.ui.detail.DetailViewModel.UiModel
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
        movieDetailToggleFavourite.setOnClickListener {
            viewModel.onToggleFavouriteMovieClicked()
        }

        viewModel.model.observe(this, Observer(::updateUI))
    }

    private fun updateUI(model: UiModel) {

        model.movie?.let {
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780/${it.backdropPath}")
            movieDetailCollapsing.title = it.title
            movieDetailToggleFavourite.setImageResource(if (it.favourite)
                R.drawable.baseline_star_white_48
            else
                R.drawable.baseline_star_border_white_48)
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
