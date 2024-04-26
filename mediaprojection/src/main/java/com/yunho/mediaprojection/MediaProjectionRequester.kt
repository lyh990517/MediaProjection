package com.yunho.mediaprojection

import android.app.Activity
import kotlinx.coroutines.flow.StateFlow

interface MediaProjectionRequester {
    fun initialize(activity: Activity)

    fun getMediaProjection(): StateFlow<MediaProjectionState>
}
