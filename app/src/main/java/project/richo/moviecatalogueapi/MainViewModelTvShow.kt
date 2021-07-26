package project.richo.moviecatalogueapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModelTvShow: ViewModel(){
    companion object {
        private const val API_KEY = "d00ab8aab94a962b57bf8b1461982032"
    }

    private val listTvShow = MutableLiveData<ArrayList<TvShow>>()

    internal fun setTvShow() {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShow>()
        val url =
            "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

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
                        val tvShow = list.getJSONObject(i)
                        val tvShowItem = TvShow()
                        tvShowItem.id = tvShow.getInt("id")
                        tvShowItem.name = tvShow.getString("original_name")
                        tvShowItem.description = tvShow.getString("overview")
                        tvShowItem.release = tvShow.getString("first_air_date")
                        tvShowItem.rating = tvShow.getInt("vote_count")
                        tvShowItem.photo = tvShow.getString("backdrop_path")
                        listItems.add(tvShowItem)
                    }
                    listTvShow.postValue(listItems)
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

    internal fun getTvShow(): LiveData<java.util.ArrayList<TvShow>> {
        return listTvShow
    }
}