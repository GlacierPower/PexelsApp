package com.glacierpower.pexelsapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.databinding.FragmentDetailsBinding
import com.glacierpower.pexelsapp.presentation.adapter.DetailsAdapter
import com.glacierpower.pexelsapp.presentation.adapter.listener.DetailsListener
import com.glacierpower.pexelsapp.utils.Constants
import com.glacierpower.pexelsapp.utils.ImageDownloader
import com.glacierpower.pexelsapp.utils.NavHelper.navigate
import com.glacierpower.pexelsapp.utils.ResultState
import com.glacierpower.pexelsapp.utils.checkMode
import com.glacierpower.pexelsapp.utils.showAlert
import com.glacierpower.pexelsapp.utils.showHide
import com.glacierpower.pexelsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, DetailsListener {

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    private var _viewBinding: FragmentDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var detailsAdapter: DetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkMode()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDetailsBinding.inflate(inflater)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getPhotoById()
        connection()
        observeExploreLD()
        explore()
        observeNavigate()
        navigateToHome()
        setupRecyclerView()

    }

    private fun navigateToHome() {
        viewBinding.btnExplore.setOnClickListener {
            viewModel.navigateToCurated()
        }

    }

    private fun observeNavigate() {
        viewModel.navCurated.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                navigate(it)
            }
        })
    }

    private fun observeExploreLD() {
        viewModel.explore.observe(viewLifecycleOwner, Observer {
            if (it) {
                showHide(viewBinding.noResultLayout)

            }
        })
    }


    private fun getPhotoById() {
        viewModel.getPhotoById(args.photoId)
        viewModel.details.observe(viewLifecycleOwner, Observer { photo ->
            when (photo) {
                is ResultState.Success -> {
                    detailsAdapter.differ.submitList(listOf(photo.data))
                    viewBinding.progressBar.visibility = View.GONE
                }

                is ResultState.Error -> {
                    toast(requireContext(), Constants.ERROR)
                }

                is ResultState.Loading -> {
                    viewBinding.shimmerView.startShimmer()
                    viewBinding.progressBar.visibility = View.VISIBLE

                }
            }
        })
    }

    private fun setupRecyclerView() {

        detailsAdapter = DetailsAdapter(this)
        viewBinding.rvDetails.apply {
            setHasFixedSize(true)
            adapter = detailsAdapter
        }


    }

    private fun explore() {
        viewBinding.btnExplore.setOnClickListener {
            viewBinding.noResultLayout.visibility = View.GONE


        }
    }


    private fun connection() {
        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            if (isOnline) {
                viewBinding.tryAgainLayout.visibility = View.GONE
                viewBinding.rvDetails.visibility = View.VISIBLE
                getPhotoById()
            } else {
                viewBinding.tryAgainLayout.visibility = View.VISIBLE
                viewBinding.rvDetails.visibility = View.GONE
                toast(requireContext(), getString(R.string.no_connection))
                showAlert()

            }
        }
    }

    override fun onRefresh() {
        getPhotoById()
    }

    override fun downloadPhoto(link: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val downloader = ImageDownloader(requireContext())
            downloader.downloadImage(link)
        }
        toast(requireContext(), getString(R.string.downloading_started))
    }

    override fun addToBookmarks(id: Int) {
        viewModel.favClicked(id)
        toast(requireContext(), getString(R.string.photo_was_added_to_bookmarks))
    }

}







