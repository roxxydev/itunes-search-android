package com.itunessearch.android.presentation.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.domain.model.Media
import com.itunessearch.android.domain.state.DataState
import com.itunessearch.android.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<MainDataState>> = MutableLiveData()

    val dataState: LiveData<DataState<MainDataState>>
        get () = _dataState

    fun setStateEvent(intent: MainIntent) {

        viewModelScope.launch {

            when(intent) {

                is MainIntent.GetInitialContentsIntent -> {

                    val termSearch = intent.term ?: ""
                    val mediaType = intent.media ?: Media.ALL

                    mainRepository.getContents(termSearch, mediaType)
                        .onEach { dataState ->
                            dataState.data?.isInitial = true
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainIntent.GetContentsIntent -> {

                    val termSearch = intent.term ?: ""
                    val mediaType = intent.media?: Media.ALL

                    mainRepository.getContents(termSearch, mediaType)
                        .onEach { dataState ->
                            dataState.data?.isInitial = false
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainIntent.None -> {}
            }
        }
    }
}

sealed class MainIntent {

    data class GetInitialContentsIntent(val term: String?, val media: Media?) : MainIntent()
    data class GetContentsIntent(val term: String?, val media: Media?) : MainIntent()
    object None : MainIntent()
}

class MainDataState(
    var isInitial: Boolean?,
    val contents: List<Content>,
    val cacheContents: List<Content>
)
