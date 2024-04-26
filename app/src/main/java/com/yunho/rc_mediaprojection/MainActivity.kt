package com.yunho.rc_mediaprojection

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.yunho.mediaprojection.MediaProjectionFactory
import com.yunho.mediaprojection.MediaProjectionState
import com.yunho.rc_mediaprojection.ui.theme.RCMediaProjectionTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mediaProjection = MediaProjectionFactory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            mediaProjection.getMediaProjection().collect {
                when (it) {
                    is MediaProjectionState.Error -> Log.e("getProjection", it.throwable.message ?: "")
                    MediaProjectionState.Idle -> {}
                    is MediaProjectionState.Success -> Log.e("getProjection", it.toString())
                }
            }
        }
        setContent {
            RCMediaProjectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mediaProjection.initialize(this@MainActivity)
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RCMediaProjectionTheme {
        Greeting("Android")
    }
}
