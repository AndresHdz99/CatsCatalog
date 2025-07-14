package org.catsproject.project.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


class ViewModelResetManager {
    private val _userChangeEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val userChangeEvent: SharedFlow<String> = _userChangeEvent

    fun notifyUserChanged(newUsername: String) {
        _userChangeEvent.tryEmit(newUsername)
    }
}
