package com.aptivist.dogchallenge.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptivist.dogchallenge.di.IODispatcher
import com.aptivist.dogchallenge.domain.models.RepositoryResponse
import com.aptivist.dogchallenge.domain.repositories.IDogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dogRepository: IDogRepository, @IODispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    private val _currentDog = mutableStateOf("")
    val currentDog : State<String>
        get() = _currentDog

    private val _uiState = mutableStateOf<MainUIState>(MainUIState.Idle)
    val uiState : State<MainUIState>
        get() = _uiState

    private val _uiActions = Channel<UIActions>()
    val uiActions = _uiActions.receiveAsFlow()

    fun getDogImage () {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = MainUIState.Loading
            when (val dog = dogRepository.getRandomDog()) {
                is RepositoryResponse.Failed -> {
                    _uiActions.send(UIActions.UIShowError(dog.errorMessage))
                }
                is RepositoryResponse.Success -> {
                    _currentDog.value = dog.data.image
                }
            }
            _uiState.value = MainUIState.Idle
        }
    }

}

sealed class UIActions {
    data class UIShowError(val message: String) : UIActions()
}

sealed class MainUIState {
    object Idle: MainUIState()
    object Loading: MainUIState()
}