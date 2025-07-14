package org.catsproject.project.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    abstract fun resetState()

    protected fun observeUserChanges(resetManager: ViewModelResetManager) {
        viewModelScope.launch {
            resetManager.userChangeEvent
                .distinctUntilChanged()
                .collect {
                    resetState()
                }
        }
    }
}
