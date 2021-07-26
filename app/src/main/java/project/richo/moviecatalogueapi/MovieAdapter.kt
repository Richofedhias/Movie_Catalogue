package project.richo.moviecatalogueapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieAdapterHolder>() {
    private val mData = ArrayList<Movie>()
    private var onItemClickCallback: OnItemClickCallback? = null

    companion object {
        const val url = "https://image.tmdb.org/t/p/original/"
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<Movie>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MovieAdapterHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MovieAdapterHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class MovieAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url + movie.photo)
                    .apply(RequestOptions().override(100, 150))
                    .into(img_poster)
                txt_name.text = movie.name
                txt_description.text = movie.description
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}