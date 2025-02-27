package dev.enesky.feature.adaptive

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.feature.adaptive.navigation.Adaptive
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@HiltViewModel
class AdaptiveViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AdaptiveUiState, AdaptiveEvent>(
    initialState = { AdaptiveUiState() }
) {

    init {
        val args: Adaptive = savedStateHandle.toRoute()
    }
}

data class AdaptiveUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
) : IUiState

sealed interface AdaptiveEvent : IEvent {
    data class OnError(override val errorMessage: String) : AdaptiveEvent, IErrorEvent
}
