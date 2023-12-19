package com.glacierpower.pexelsapp.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.databinding.FragmentDetailsBinding
import com.glacierpower.pexelsapp.presentation.MainActivity
import com.glacierpower.pexelsapp.utils.Constants
import com.glacierpower.pexelsapp.utils.ImageDownloader
import com.glacierpower.pexelsapp.utils.NavHelper.navigate
import com.glacierpower.pexelsapp.utils.ResultState
import com.glacierpower.pexelsapp.utils.showHide
import com.glacierpower.pexelsapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsFragmentArgs by navArgs()

    private var _viewBinding: FragmentDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val color = ContextCompat.getColor(requireContext(), R.color.white)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(color.toDrawable())
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
        viewModel.getPhotoById(args.photoId)
        getPhotoById()

        viewBinding.btnBookmark.setOnClickListener {
            viewModel.favClicked(args.photoId)
            toast(requireContext(), getString(R.string.photo_was_added_to_bookmarks))
        }
        viewBinding.btnDownload.setOnClickListener {
            downloadImage(requireContext(), args.photoLink)
            toast(requireContext(), getString(R.string.downloading_started))
        }

        connection()
        observeExploreLD()
        explore()
        observeNavigate()
        navigateToHome()

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
                showHide(viewBinding.swipeContainer)
            }
        })
    }


    private fun getPhotoById() {

        viewModel.details.observe(viewLifecycleOwner, Observer { photo ->
            when (photo) {
                is ResultState.Success -> {
                    Glide.with(viewBinding.photo)
                        .load(photo.data.src.large)
                        .placeholder(R.drawable.place_holder)
                        .into(viewBinding.photo)
                    (requireActivity() as MainActivity).supportActionBar?.title =
                        photo.data.photographer
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.btnDownload.visibility = View.VISIBLE
                    viewBinding.btnBookmark.visibility = View.VISIBLE
                }

                is ResultState.Error -> {
                    toast(requireContext(), Constants.ERROR)
                }

                is ResultState.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    viewBinding.btnDownload.visibility = View.GONE
                    viewBinding.btnBookmark.visibility = View.GONE
                }
            }
        })
    }

    private fun downloadImage(context: Context, imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val downloader = ImageDownloader(context)
            downloader.downloadImage(imageUrl)

        }

    }

    private fun explore() {
        viewBinding.btnExplore.setOnClickListener {
            viewBinding.noResultLayout.visibility = View.GONE


        }
    }


    private fun connection() {
        viewModel.connection.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it) {
                    showHide(viewBinding.tryAgainLayout)

                } else {
                    getPhotoById()
                    viewBinding.tryAgainLayout.visibility = View.GONE
                }
            }
        })
    }

    override fun onRefresh() {
        getPhotoById()
    }


}







