package sg.nrk.openmoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sg.nrk.openmoviedb.databinding.FragmentMovieInfoBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieInfoFragment : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this.requireActivity()).get(MovieViewModel::class.java)
    }

    val movieId :String? by lazy {
        arguments?.getString(ARG_MOVIE_ID,null)
    }

     lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_info, container, false)
        binding.lifecycleOwner = this
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         viewLifecycleOwner.lifecycleScope.launchWhenCreated {
             movieId?.let {
                 val movieInfo = withContext(Dispatchers.IO){viewModel.getMovieInfo(it)}
                 withContext(Dispatchers.Main){
                     binding.item = movieInfo
                     binding.executePendingBindings()
                 }
             }
        }

        viewModel.getSearchResults().observe(viewLifecycleOwner, {
            if(it?.consumed==false && !viewModel.resetSearch){
                findNavController().navigate(R.id.action_MovieInfoFragment_to_MovieListFragment)
            }

        })

    }

    companion object{
        const val ARG_MOVIE_ID = "MOVIE_ID"
    }
}