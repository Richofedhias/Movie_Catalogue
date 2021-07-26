package project.richo.moviecatalogueapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModelMovie : ViewModel() {
    companion object {
        private const val API_KEY = "d00ab8aab94a962b57bf8b1461982032"
    }

    private val listMovie = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url =
            "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItem = Movie()
                        movieItem.id = movie.getInt("id")
                        movieItem.name = movie.getString("title")
                        movieItem.description = movie.getString("overview")
                        movieItem.release = movie.getString("release_date")
                        movieItem.rating = movie.getInt("vote_average")
                        movieItem.photo = movie.getString("backdrop_path")
                        listItems.add(movieItem)
                    }
                    listMovie.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("Exception", error.message.toString())
            }

        })
    }

    internal fun getMovie(): LiveData<java.util.ArrayList<Movie>> {
        return listMovie
    }
}