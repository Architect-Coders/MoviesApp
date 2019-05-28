package es.afmsoft.moviesapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import es.afmsoft.moviesapp.R
import es.afmsoft.moviesapp.getViewModel
import es.afmsoft.moviesapp.loadUrl
import es.afmsoft.moviesapp.model.Movie
import es.afmsoft.moviesapp.ui.detail.DetailViewModel.UiModel;
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var modelView: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent?.getParcelableExtra<Movie>(MOVIE)

        modelView = getViewModel { DetailViewModel(movie!!) }
        modelView.model.observe(this, Observer(::updateUI))
    }

    private fun updateUI (model: UiModel) {

        when(model) {
            is UiModel.Content -> with(model.movie) {
                main_backdrop.loadUrl("https://image.tmdb.org/t/p/w1280/${backdropPath}")
                supportActionBar?.title = title
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
        val MOVIE = "Movie"
    }
}
