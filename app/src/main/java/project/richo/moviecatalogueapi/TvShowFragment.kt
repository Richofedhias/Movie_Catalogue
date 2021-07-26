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
import kotlinx.android.synthetic.main.fragment_tv_show.*

class TvShowFragment : Fragment() {
    private lateinit var adapter: TvShowAdapter
    private lateinit var mainViewModelTvShow: MainViewModelTvShow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_tvshow.setHasFixedSize(true)

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        rv_tvshow.layoutManager = LinearLayoutManager(activity)
        rv_tvshow.adapter = adapter

        mainViewModelTvShow = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModelTvShow::class.java)

        mainViewModelTvShow.setTvShow()
        showLoading(true)

        mainViewModelTvShow.getTvShow().observe(this, Observer { tvShowItems ->
            if (tvShowItems != null) {
                adapter.setData(tvShowItems)
                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("Category", "TvShow")
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
