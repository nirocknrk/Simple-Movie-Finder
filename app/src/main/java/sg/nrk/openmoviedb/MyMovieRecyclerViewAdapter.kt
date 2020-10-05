package sg.nrk.openmoviedb

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import sg.nrk.openmoviedb.model.Movie

class MyMovieRecyclerViewAdapter(
    private var values: List<Movie>,
    val onItemSelected:(Movie)->Unit,
) : RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.yearView.text = item.year
        holder.titleView.text = item.title

        item.poster?.let { imageUrl ->
            Glide.with(holder.imageView.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView)

        }
        holder.rootView.setOnClickListener { onItemSelected.invoke(item) }
    }

    override fun getItemCount(): Int = values.size

    fun update(newList: List<Movie>){
        this.values = newList
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val yearView: TextView = view.findViewById(R.id.movieYear)
        val titleView: TextView = view.findViewById(R.id.movieTitle)
        val imageView: ImageView = view.findViewById(R.id.moviePoster)
        val rootView: View = view.findViewById(R.id.root)

        override fun toString(): String {
            return super.toString() + " '" + titleView.text + "'"
        }
    }

}