package com.glacierpower.pexelsapp.presentation.home

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.data.sharedpreferences.UiMode
import com.glacierpower.pexelsapp.databinding.FragmentHomeBinding
import com.glacierpower.pexelsapp.presentation.adapter.CuratedPhotoAdapter
import com.glacierpower.pexelsapp.presentation.adapter.FeaturedAdapter
import com.glacierpower.pexelsapp.presentation.adapter.listener.CuratedListener
import com.glacierpower.pexelsapp.presentation.adapter.listener.FeaturedListener
import com.glacierpower.pexelsapp.presentation.utils.ScrollListener
import com.glacierpower.pexelsapp.presentation.utils.Scroller
import com.glacierpower.pexelsapp.utils.Constants.DELAY
import com.glacierpower.pexelsapp.utils.Constants.ERROR
import com.glacierpower.pexelsapp.utils.ResultState
import com.glacierpower.pexelsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(),
    SwipeRefreshLayout.OnRefreshListener, FeaturedListener, CuratedListener {


    private val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var featuredAdapter: FeaturedAdapter

    private lateinit var curatedPhotoAdapter: CuratedPhotoAdapter

    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.theme.asLiveData().observe(viewLifecycleOwner) { uiMode ->
            setCheckedMode(uiMode)
        }
        setupRecyclerView()
        initScrollListeners()
        getPhotos()
        getFeatured()
        explore()
        tryAgain()
        searchPhoto()
        getCurated()
        connection()
        observeExploreLD()
        loading()
        viewModel.getFeatured()
        viewModel.insertPhotosCuratedPhoto()
    }

    private fun explore() {
        viewBinding.btnExplore.setOnClickListener {
            viewBinding.searchView.apply {
                setQuery("", false)
                clearFocus()
            }
            getCurated()
            viewBinding.noResultLayout.visibility = View.GONE
            viewBinding.swipeContainer.visibility = View.VISIBLE

        }
    }

    private fun tryAgain() {
        viewBinding.btnTryAgain.setOnClickListener {
            viewModel.connection.observe(viewLifecycleOwner, Observer {
                it.let {
                    if (it) {
                        viewBinding.tryAgainLayout.visibility = View.VISIBLE
                        viewBinding.photoLayout.visibility = View.GONE
                    } else {
                        getCurated()
                        viewBinding.tryAgainLayout.visibility = View.GONE
                    }
                }
            })

        }
    }

    private fun initScrollListeners() {

        with(viewBinding) {
            rvPhoto.addOnScrollListener(object : ScrollListener(
                rvPhoto.layoutManager as StaggeredGridLayoutManager
            ) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.getFeatured()
                }
            })
        }

        viewBinding.rvPhoto.addOnScrollListener(object : Scroller() {
            override fun show() {
                viewModel.showHide(viewBinding.searchLayout)
            }

            override fun hide() {
                viewModel.showHide(viewBinding.searchLayout)
            }
        })
    }

    private fun observeExploreLD() {
        viewModel.explore.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.showHide(viewBinding.noResultLayout)
                viewModel.showHide(viewBinding.swipeContainer)
            }
        })
    }

    private fun searchPhoto() {
        viewModel.search.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ResultState.Success -> {

                    curatedPhotoAdapter.differ.submitList(response.data)
                    showHideProgressBar(false)

                }

                is ResultState.Error -> {
                    toast(requireContext(), ERROR)
                    showHideProgressBar(false)
                }

                is ResultState.Loading -> {
                    viewBinding.shimmerView.startShimmer()
                    viewBinding.tryAgainLayout.visibility = View.GONE
                }

            }
        })

        viewBinding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    job?.cancel()
                    job = viewLifecycleOwner.lifecycleScope.launch {
                        delay(DELAY)
                        if (newText?.length!! >= 3) {
                            viewModel.getSearchedPhoto(newText)
                            showHideProgressBar(true)
                            viewModel.insertSearchedPhoto(newText)
                        }
                    }
                    return false
                }


            }

        )

    }

    private fun getCurated() {

        viewModel.curatedPhoto.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        viewBinding.flipper.displayedChild = 1
                    } else {
                        curatedPhotoAdapter.differ.submitList(it.data)
                        showHideProgressBar(false)
                        viewBinding.flipper.displayedChild = 0
                    }

                }

                is ResultState.Error -> {
                    toast(requireContext(), ERROR)
                    showHideProgressBar(false)
                }

                is ResultState.Loading -> {
                    showHideProgressBar(true)
                    viewBinding.tryAgainLayout.visibility = View.GONE
                }

            }
        })
    }

    private fun getFeatured() {

        viewModel.featureCollections.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Success -> {
                    featuredAdapter.differ.submitList(it.data)
                }

                is ResultState.Error -> {
                    toast(requireContext(), ERROR)
                    showHideProgressBar(false)
                }

                is ResultState.Loading -> {
                    showHideProgressBar(true)
                    viewBinding.tryAgainLayout.visibility = View.GONE

                }
            }
        })
    }

    private fun setupRecyclerView() {

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewBinding.rvPhoto.layoutManager = layoutManager
        viewBinding.swipeContainer.setOnRefreshListener(this)

        featuredAdapter = FeaturedAdapter(this)
        viewBinding.rvSearch.apply {
            layoutManager.scrollToPosition(1)
            adapter = featuredAdapter
        }

        curatedPhotoAdapter = CuratedPhotoAdapter(this)
        viewBinding.rvPhoto.apply {
            setHasFixedSize(true)
            adapter = curatedPhotoAdapter
        }


    }

    override fun onRefresh() {
        viewModel.clearQuery()
        viewModel.getCuratedPhoto()
    }

    private fun connection() {
        viewModel.connection.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it) {
                    viewModel.showHide(viewBinding.tryAgainLayout)
                    viewModel.showHide(viewBinding.photoLayout)
                    toast(requireContext(), getString(R.string.no_connection))
                } else {
                    getCurated()
                    viewBinding.tryAgainLayout.visibility = View.GONE
                }
            }
        })
    }

    private fun showHideProgressBar(isVisible: Boolean) {
        viewBinding.swipeContainer.isRefreshing = isVisible
    }


    private fun getPhotos() {
        viewModel.getCuratedPhoto()
        showHideProgressBar(true)
        viewModel.getFeatured()
    }

    private fun loading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewBinding.swipeContainer.isRefreshing = it
            } else {
                viewBinding.swipeContainer.isRefreshing != it
            }
        })
    }

    override fun getFeaturedPhoto(query: String) {
        viewModel.getSearchedPhoto(query)
        val str = getIntent(query).getStringExtra(query)
        viewBinding.searchView.setQuery(str, true)
        showHideProgressBar(true)
        viewModel.insertSearchedPhoto(query)
    }



    override fun getPhotoById(photoId: Int, link: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(link, photoId)
        findNavController().navigate(
            action
        )
    }

    private fun setCheckedMode(uiMode: UiMode?) {
        when (uiMode) {
            UiMode.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewBinding.darkMode.isChecked = false
            }

            UiMode.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(),R.color.black )
                viewBinding.darkMode.isChecked = true
            }

            else -> {}
        }
    }

    private fun initView() {
        viewBinding.darkMode.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when (isChecked) {
                    true -> viewModel.setMode(UiMode.DARK)
                    false -> viewModel.setMode(UiMode.LIGHT)
                }
            }
        }
    }


}


