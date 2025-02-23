package dev.enesky.core.common.data.base

import androidx.lifecycle.ViewModel
import dev.enesky.core.common.data.delegate.*

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 *
 * Base ViewModel class for all ViewModels
 * It provides UiState and Event delegation with the help of UiStateDelegate and EventDelegate
 */
abstract class BaseViewModel<S : IUiState, E : IEvent>(
    initialState: () -> S
) : ViewModel(),
    UiState<S> by UiStateDelegate(initialState = initialState),
    Event<E> by EventDelegate()
