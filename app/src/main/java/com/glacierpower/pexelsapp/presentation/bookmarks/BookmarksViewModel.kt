package com.glacierpower.pexelsapp.presentation.bookmarks

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.domain.pexels.PexelsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val pexelsInteractor: PexelsInteractor) :
    ViewModel() {
    val photo = flow {
        emit(pexelsInteractor.getPhotosFromBookmarksDataBase())
    }
    private var _toast = MutableLiveData<Int>()
    val toast: LiveData<Int> get() = _toast
    private var _navCurated = MutableLiveData<Int?>()
    val navCurated: LiveData<Int?> get() = _navCurated

    fun navigateToCurated() {
        _navCurated.value = R.id.homeFragment
    }

    fun deletePhoto(id: Int) {
        viewModelScope.launch {
            try {
                pexelsInteractor.deleteFormBookmarks(id)
            } catch (e: Exception) {
                _toast.value = View.VISIBLE
            }

        }

    }

}
