package com.yunho.mediaprojection

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaProjectionRequesterImpl : MediaProjectionRequester {
    private lateinit var mediaProjectionManager: MediaProjectionManager
    private lateinit var mediaProjectionLauncher: ActivityResultLauncher<Intent>
    private val mediaProjection = MutableStateFlow<MediaProjectionState>(MediaProjectionState.Idle)

    override fun getMediaProjection(): StateFlow<MediaProjectionState> = mediaProjection.asStateFlow()

    override fun initialize(activity: Activity) {
        mediaProjectionLauncher =
            (activity as ComponentActivity).registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
            ) { result ->
                result?.data?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        mediaProjection.value = MediaProjectionState.Success(it)
                    }
                } ?: {
                    mediaProjection.value = MediaProjectionState.Error(Exception("null projection"))
                }
            }

        mediaProjectionManager =
            (activity.getSystemService(Service.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager)
        val mediaProjectionIntent = mediaProjectionManager.createScreenCaptureIntent()
        mediaProjectionLauncher.launch(mediaProjectionIntent)
    }
}
