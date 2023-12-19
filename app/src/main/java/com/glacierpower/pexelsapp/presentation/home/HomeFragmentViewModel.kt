package com.glacierpower.pexelsapp.presentation.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glacierpower.pexelsapp.domain.PexelsInteractor
import com.glacierpower.pexelsapp.model.CollectionModel
import com.glacierpower.pexelsapp.model.PhotoListModel
import com.glacierpower.pexelsapp.utils.Constants
import com.glacierpower.pexelsapp.utils.InternetConnection
import com.glacierpower.pexelsapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val pexelsInteractor: PexelsInteractor,
) :
    ViewModel() {

    @Inject
    lateinit var internetConnection: InternetConnection

    private var _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> get() = _connection

    private var _featuredCollections = MutableLiveData<ResultState<List<CollectionModel>>>()
    val featureCollections: LiveData<ResultState<List<CollectionModel>>> get() = _featuredCollections

    private var _curatedPhoto = MutableLiveData<ResultState<List<PhotoListModel>>>()
    val curatedPhoto: LiveData<ResultState<List<PhotoListModel>>> get() = _curatedPhoto

    private var _search = MutableLiveData<ResultState<List<PhotoListModel>>>()
    val search: LiveData<ResultState<List<PhotoListModel>>> get() = _search

    private var _explore = MutableLiveData<Boolean>()
    val explore: LiveData<Boolean> get() = _explore

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    private var queryString: String? = null
    var pageNumber = (1..100).random()

    fun insertPhotosCuratedPhoto() {
        viewModelScope.launch {
            try {
                pexelsInteractor.insertPhoto(pageNumber)
            } catch (e: Exception) {
                Log.w("Insert data", e.message.toString())
            }
        }
    }

    fun insertSearchedPhoto(query:String){
        viewModelScope.launch {
            try {
                pexelsInteractor.insertSearchedPhoto(query,pageNumber)
            }catch (e:Exception){
                Log.w("Insert searched", e.message.toString())
            }
        }
    }

    fun getCuratedPhoto() {
        try {
            viewModelScope.launch {
                if (internetConnection.isOnline()) {
                    when (val response = pexelsInteractor.getCuratedPhoto(pageNumber)) {
                        is ResultState.Success -> {
                            _curatedPhoto.postValue(ResultState.Success(response.data))
                            _connection.value = false
                            _loading.value = false
                        }

                        is ResultState.Loading -> {
                            _loading.value = true
                        }

                        is ResultState.Error -> {
                            _curatedPhoto.postValue(ResultState.Error(Constants.ERROR))
                            _loading.value = false
                        }

                    }

                } else {
                    _connection.value = true
                    _curatedPhoto.postValue(ResultState.Error(Constants.NO_CONNECTION))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> _curatedPhoto.postValue(ResultState.Error(exception.message!!))

            }

        }
    }


    fun getFeatured() {
        viewModelScope.launch {
            try {
                if (internetConnection.isOnline()) {
                    when (val response = pexelsInteractor.getFeaturedCollections(pageNumber, 7)) {
                        is ResultState.Success -> {
                            _featuredCollections.postValue(ResultState.Success(response.data))
                            _connection.value = false
                            _loading.value = false
                        }

                        is ResultState.Error -> {
                            _curatedPhoto.postValue(ResultState.Error(Constants.ERROR))
                            _loading.value = false
                        }

                        is ResultState.Loading -> {
                            _loading.value = true
                        }
                    }

                } else {
                    _connection.value = true
                    _featuredCollections.postValue(ResultState.Error(Constants.NO_CONNECTION))
                }
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> _featuredCollections.postValue(ResultState.Error(exception.message!!))

                }

            }
        }
    }

    fun getSearchedPhoto(query: String) {
        try {
            viewModelScope.launch {
                if (internetConnection.isOnline()) {
                    when (val response = pexelsInteractor.getSearchedPhoto(query, pageNumber)) {
                        is ResultState.Success -> {
                            if (response.data.isNullOrEmpty()) {
                                _explore.value = true
                            } else {
                                _search.postValue(ResultState.Success(response.data))
                                _connection.value = false
                                _explore.value = false
                                _loading.value = false
                            }

                        }

                        is ResultState.Error -> {
                            _loading.value = false
                            _search.postValue(ResultState.Error(Constants.ERROR))
                        }

                        is ResultState.Loading -> {
                            _loading.value = true
                        }
                    }
                } else {
                    _connection.value = true
                    _search.postValue(ResultState.Error(Constants.NO_CONNECTION))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> _search.postValue(ResultState.Error(exception.message!!))

            }

        }
    }


    fun clearQuery() {
        queryString = null

    }

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }


}


