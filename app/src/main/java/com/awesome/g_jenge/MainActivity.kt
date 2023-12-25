package com.awesome.g_jenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.g_jenge.compose.Display
import com.awesome.g_jenge.ui.theme.GjengeTheme
import com.awesome.g_jenge.viewmodel.AppViewModel

class MainActivity : ComponentActivity(), Display {
    private lateinit var appViewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = AppViewModel(this.application)
        setContent {
            GjengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    //launch the main display
                    mainscreen(appViewModel)
                }
            }
        }
    }
}