package com.itunessearch.android.presentation.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.itunessearch.android.domain.model.Content
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

    private val _dataState: MutableLiveData<DataState<DetailDataState>> = MutableLiveData()

    val dataState: LiveData<DataState<DetailDataState>>
        get () = _dataState

    fun setStateEvent(intent: DetailIntent) {

        viewModelScope.launch {

            when(intent) {

                is DetailIntent.ShowContentDetail -> {

                    mainRepository.getContent(intent.id, intent.trackId)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class DetailIntent {

    data class ShowContentDetail(val id: Int?, val trackId: Int?) : DetailIntent()
}

class DetailDataState(
    val content: Content
)
