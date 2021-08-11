package com.powilliam.android.chatting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.powilliam.android.chatting.ui.ChattingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChattingTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Text(
                        text = getString(R.string.app_name),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}