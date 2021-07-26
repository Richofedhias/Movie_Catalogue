package project.richo.moviecatalogueapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.progressBar
import kotlinx.coroutines.*
import java.lang.Exception

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL = "detail"
        const val url = "https://image.tmdb.org/t/p/original/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val category = intent.getStringExtra("Category")
        showLoading(true)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                withContext(Dispatchers.Main) {
                    if (category == "Movie") {
                        val movie = intent.getParcelableExtra(DETAIL) as Movie
                        title = movie.name
                        text_name.text = movie.name
                        text_description.text = movie.description
                        text_release.text = movie.release
                        text_rating.text = movie.rating.toString()
                        Glide.with(this@DetailActivity)
                            .load(url + movie.photo)
                            .into(image_poster)
                        showLoading(false)
                    } else if (category == "TvShow") {
                        val tvShow = intent.getParcelableExtra(DETAIL) as TvShow
                        title = tvShow.name
                        text_name.text = tvShow.name
                        text_description.text = tvShow.description
                        text_release.text = tvShow.release
                        text_rating.text = tvShow.rating.toString()
                        Glide.with(this@DetailActivity)
                            .load(url + tvShow.photo)
                            .into(image_poster)
                        showLoading(false)
                    }
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}