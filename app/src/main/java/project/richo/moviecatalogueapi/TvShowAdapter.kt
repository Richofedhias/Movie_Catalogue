package project.richo.moviecatalogueapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class TvShowAdapter: RecyclerView.Adapter<TvShowAdapter.TvShowHolder>() {
    private val mData = ArrayList<TvShow>()
    private var onItemClickCallback: OnItemClickCallback? = null

    companion object {
        const val url = "https://image.tmdb.org/t/p/original/"
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<TvShow>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return TvShowHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class TvShowHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(tvShow: TvShow) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(url + tvShow.photo)
                    .apply(RequestOptions().override(100, 150))
                    .into(img_poster)
                txt_name.text = tvShow.name
                txt_description.text = tvShow.description
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tvShow) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }
}