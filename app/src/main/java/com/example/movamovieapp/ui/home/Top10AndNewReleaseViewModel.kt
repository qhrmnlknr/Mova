package com.example.movamovieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movamovieapp.api.NetworkResponse
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.datamodels.newmovies.Result
import com.example.movamovieapp.utils.enums.CardTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Top10AndNewReleaseViewModel @Inject constructor(private val repostory: Repostory) : ViewModel() {



    private val _listUiStateData = MutableLiveData<HomeUiState>()
    val listUiStateData: LiveData<HomeUiState> get() = _listUiStateData

    fun selectRequestType(type : CardTypeEnum){
        when (type){
            CardTypeEnum.MOVIE_LIST -> getnewmovielist()
            else -> getDiscover()
        }
    }

    private fun getnewmovielist(){
        viewModelScope.launch {
            repostory.getMovies2().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _listUiStateData.postValue(HomeUiState.Loading)

                    is NetworkResponse.Success -> _listUiStateData.postValue(it.let { it1 ->
                        HomeUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _listUiStateData.postValue(it.message.let { it1 ->
                        HomeUiState.Error(
                            it1
                        )
                    })
                }
            }

        }
    }

    private fun getDiscover(){
        viewModelScope.launch {
            repostory.getNewTvs().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _listUiStateData.postValue(HomeUiState.Loading)

                    is NetworkResponse.Success -> _listUiStateData.postValue(it.let { it1 ->
                        HomeUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _listUiStateData.postValue(it.message.let { it1 ->
                        HomeUiState.Error(
                            it1
                        )
                    })
                }
            }

        }
    }


}