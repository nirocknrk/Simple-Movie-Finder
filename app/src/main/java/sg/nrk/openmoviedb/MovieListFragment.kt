package sg.nrk.openmoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import sg.nrk.openmoviedb.model.Movie

import androidx.navigation.fragment.findNavController
import sg.nrk.openmoviedb.MovieInfoFragment.Companion.ARG_MOVIE_ID
import sg.nrk.openmoviedb.utils.SpacesItemDecoration


class MovieListFragment : Fragment() {

    private var columnCount = 2

    val movieAdapter by lazy {
        MyMovieRecyclerViewAdapter(emptyList(),::onMovieSelected)
    }

    val viewModel by lazy {
        ViewModelProvider(this.requireActivity()).get(MovieViewModel::class.java)
    }

    lateinit var recyclerView:RecyclerView
    lateinit var descriptionTextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.movieList)
        descriptionTextView = view.findViewById<TextView>(R.id.descriptionLabel)

        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = movieAdapter
            addItemDecoration(SpacesItemDecoration(context.resources.getDimensionPixelOffset(R.dimen.movie_list_item_space))
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showEmptyView(true)

        viewModel.getSearchResults().observe(viewLifecycleOwner, {
            if(it?.consumed==false){
                val movieList = it.consume()?: emptyList()
                movieAdapter.update(movieList)
                showEmptyView(movieList.isNullOrEmpty())
            }

        })

    }

    fun showEmptyView(isShow:Boolean){
        if(isShow){
            descriptionTextView.setText(
                if(viewModel.resetSearch) R.string.initial_message
                else R.string.no_result_found)
            descriptionTextView.visibility = View.VISIBLE
        }else{
            descriptionTextView.visibility = View.GONE
        }


    }

    fun onMovieSelected(movie: Movie){
        val bundle = Bundle().apply {
            putString(ARG_MOVIE_ID,movie.imdbID)
        }
        findNavController().navigate(R.id.action_MovieListFragment_to_MovieInfoFragment,bundle)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}