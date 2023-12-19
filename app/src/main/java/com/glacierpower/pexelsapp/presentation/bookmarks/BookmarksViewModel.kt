package com.glacierpower.pexelsapp.presentation.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.domain.PexelsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private val pexelsInteractor: PexelsInteractor) :
    ViewModel() {
    val photo = flow {
        emit(pexelsInteractor.getPhotosFromBookmarksDataBase())
    }
    private var _navCurated = MutableLiveData<Int?>()
    val navCurated: LiveData<Int?> get() = _navCurated

    fun navigateToCurated() {
        _navCurated.value = R.id.homeFragment
    }

}
