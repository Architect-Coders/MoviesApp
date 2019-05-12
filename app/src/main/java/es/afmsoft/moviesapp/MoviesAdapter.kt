package es.afmsoft.moviesapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.afmsoft.moviesapp.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val listener: (Movie) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.movie_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.movieTitle.text = movie.title
            itemView.moviePoster.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}
