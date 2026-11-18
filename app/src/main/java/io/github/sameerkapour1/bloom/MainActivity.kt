package io.github.sameerkapour1.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.sameerkapour1.bloom.core.designsystem.BloomTheme
import io.github.sameerkapour1.bloom.navigation.BloomApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                BloomApp()
            }
        }
    }
}
