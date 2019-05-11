package net.ldasilva.expertisetest.ui.movie.list.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import net.ldasilva.expertisetest.R
import net.ldasilva.expertisetest.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.movies_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.ldasilva.expertisetest.data.db.entity.MovieEntry
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import com.mancj.materialsearchbar.MaterialSearchBar
import net.ldasilva.expertisetest.data.SearchMovieType
import net.ldasilva.expertisetest.ui.movie.list.MovieItem
import net.ldasilva.expertisetest.ui.movie.list.MovieListViewModel
import net.ldasilva.expertisetest.ui.movie.list.MovieListViewModelFactory
import org.kodein.di.generic.instance

abstract class MovieListFragment : ScopedFragment(), KodeinAware,
        MaterialSearchBar.OnSearchActionListener{

    override val kodein by closestKodein()
    private val viewModelFactory : MovieListViewModelFactory by instance()

    protected lateinit var viewModel: MovieListViewModel

    abstract val searchMovieType: SearchMovieType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

        // bar config
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        searchBar.setOnSearchActionListener(this);

        bindUI()

        // get data
        getMovies(searchMovieType = searchMovieType)
    }

    private fun bindUI(query: String = "") = launch(Dispatchers.Main) {
        viewModel.movies.observe(this@MovieListFragment, Observer { newValues ->
            if (newValues == null) return@Observer

            group_loading.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            initRecyclerView(newValues.toFutureMovieItems())
        })
    }

    private fun initRecyclerView(items: List<MovieItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? MovieItem)?.let {
                showMovieDetail(it.movieEntry.id, view)
            }
        }
    }

    // search bar events
    override fun onSearchStateChanged(enabled: Boolean) {
        if (!enabled)
            getMovies(searchMovieType = searchMovieType)
    }

    override fun onSearchConfirmed(text: CharSequence) {
        getMovies(text.toString(), SearchMovieType.FILTER)
    }

    override fun onButtonClicked(buttonCode: Int) {}

    // go to details
    private fun showMovieDetail(id: Long, view: View) {
        val actionDetail = MostPopularFragmentDirections.actionDetail(id)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    // helpers
    private fun getMovies(query: String = "a", searchMovieType: SearchMovieType) = launch(Dispatchers.Main) {
        group_loading.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        viewModel.getMovies(query, searchMovieType)
    }

    private fun List<MovieEntry>.toFutureMovieItems() : List<MovieItem> {
        return this.map {
            MovieItem(it)
        }
    }
}
