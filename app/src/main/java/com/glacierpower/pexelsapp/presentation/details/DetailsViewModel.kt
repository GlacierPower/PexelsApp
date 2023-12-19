package com.glacierpower.pexelsapp.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.domain.PexelsInteractor
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.utils.Constants
import com.glacierpower.pexelsapp.utils.InternetConnection
import com.glacierpower.pexelsapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val pexelsInteractor: PexelsInteractor) :
    ViewModel() {

    @Inject
    lateinit var internetConnection: InternetConnection

    private var _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> get() = _connection

    private var _details = MutableLiveData<ResultState<PhotoListModel>>()
    val details: LiveData<ResultState<PhotoListModel>> get() = _details

    private var _explore = MutableLiveData<Boolean>()
    val explore: LiveData<Boolean> get() = _explore

    private var _navCurated = MutableLiveData<Int?>()
    val navCurated: LiveData<Int?> get() = _navCurated


    fun navigateToCurated() {
        _navCurated.value = R.id.homeFragment
    }
    fun favClicked(id:Int) {
        viewModelScope.launch {
            try {
                pexelsInteractor.insertPhotoToBookmarksDataBase(id)
            } catch (e: Exception) {
                Log.w("News fav clicked", e.message.toString())
            }

        }
    }


    fun getPhotoById(id: Int) {
        _details.value = (ResultState.Loading())
        try {
            viewModelScope.launch {
                if (internetConnection.isOnline()) {
                    when (val response = pexelsInteractor.getPhotoById(id)) {
                        is ResultState.Success -> {
                            when (response.data == null) {
                                true -> _explore.value = true
                                else -> {
                                    _details.postValue(ResultState.Success(response.data))
                                    _connection.value = false
                                }
                            }

                        }

                        is ResultState.Error -> {
                            _details.postValue(ResultState.Error(Constants.ERROR))
                        }


                        else -> {
                            _details.postValue(ResultState.Error(Constants.NO_CONNECTION))
                        }
                    }
                } else {
                    _connection.value = true
                    _details.postValue(ResultState.Error(Constants.NO_CONNECTION))
                }
            }
        } catch (exception: Exception) {
            _details.postValue(ResultState.Error(exception.message!!))

        }

    }
}

