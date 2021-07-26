package project.richo.moviecatalogueapi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {
    private lateinit var adapter: MovieAdapter
    private lateinit var mainViewModelMovie: MainViewModelMovie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movie.setHasFixedSize(true)

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        rv_movie.layoutManager = LinearLayoutManager(activity)
        rv_movie.adapter = adapter

        mainViewModelMovie = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModelMovie::class.java)

        mainViewModelMovie.setMovie()
        showLoading(true)

        mainViewModelMovie.getMovie().observe(this, Observer { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("Category", "Movie")
                intent.putExtra(DetailActivity.DETAIL, data)
                startActivity(intent)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
