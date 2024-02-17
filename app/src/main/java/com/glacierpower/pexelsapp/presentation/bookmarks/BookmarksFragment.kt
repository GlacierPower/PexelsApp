package com.glacierpower.pexelsapp.presentation.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.databinding.FragmentBookmarksBinding
import com.glacierpower.pexelsapp.presentation.adapter.BookmarksAdapter
import com.glacierpower.pexelsapp.presentation.adapter.listener.BookmarksListener
import com.glacierpower.pexelsapp.presentation.utils.ScrollListener
import com.glacierpower.pexelsapp.presentation.utils.Scroller
import com.glacierpower.pexelsapp.utils.NavHelper.navigate
import com.glacierpower.pexelsapp.utils.checkMode
import com.glacierpower.pexelsapp.utils.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment : Fragment(), BookmarksListener {

    private var _viewBinding: FragmentBookmarksBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: BookmarksViewModel by viewModels()

    private lateinit var bookmarksAdapter: BookmarksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        checkMode()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentBookmarksBinding.inflate(inflater)
        return viewBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initScrollListeners()
        getPhotoFromDB()
        observeNavigate()
        explore()

    }

    private fun explore() {
        viewBinding.btnExploreBookmarks.setOnClickListener {
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

    private fun getPhotoFromDB() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photo.catch {
                toast(requireContext(), it.message.toString())
            }.collect { list ->
                list.collect {
                    if (it.isNullOrEmpty()) {
                        viewBinding.flipper.displayedChild = 1

                    } else {
                        viewBinding.flipper.displayedChild = 0
                        bookmarksAdapter.differ.submitList(it)
                        showHideProgressBar(false)
                    }

                }
            }
        }
    }


    private fun initScrollListeners() {

        with(viewBinding) {
            rvPhoto.addOnScrollListener(object : ScrollListener(
                rvPhoto.layoutManager as StaggeredGridLayoutManager
            ) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.photo
                }
            })
        }

        viewBinding.rvPhoto.addOnScrollListener(object : Scroller() {
            override fun show() {

            }

            override fun hide() {

            }
        })
    }

    private fun setupRecyclerView() {

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        viewBinding.rvPhoto.layoutManager = layoutManager

        bookmarksAdapter = BookmarksAdapter(this)
        viewBinding.rvPhoto.apply {
            setHasFixedSize(true)
            adapter = bookmarksAdapter

        }
    }

    override fun getPhotoById(id: Int, link: String) {
        val action =
            BookmarksFragmentDirections.actionBookmarksFragmentToDetailsFragment(link, id)
        findNavController().navigate(
            action
        )
    }

    override fun deleteFormBookmarks(id: Int) {
        deletePhotoAlert(id)
    }

    private fun showHideProgressBar(isVisible: Boolean) {
        viewBinding.swipeContainer.isRefreshing = isVisible
    }

    private fun deletePhotoAlert(id: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(R.drawable.remove)
            .setTitle(getString(R.string.delete_photo))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getText(R.string.yes)) { _, _ ->
                viewModel.deletePhoto(id)
                toast(requireContext(), getString(R.string.photo_was_deleted_from_favorite))
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
            }
            .show()
    }


}