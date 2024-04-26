package com.yunho.mediaprojection

import android.content.Intent

sealed class MediaProjectionState {
    data object Idle : MediaProjectionState()

    data class Success(val intent: Intent) : MediaProjectionState()

    data class Error(val throwable: Throwable) : MediaProjectionState()
}
