package com.awesome.g_jenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.awesome.g_jenge.compose.Display
import com.awesome.g_jenge.ui.theme.GjengeTheme
import com.awesome.g_jenge.viewmodel.AppViewModel

class MainActivity : ComponentActivity(),Display {
    //
    private lateinit var appViewModel: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = AppViewModel(applicationContext)

        setContent {
            GjengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //DetailsContent()
                   gridView(appViewModel)
                   //DetailsContent(appViewModel)
                }
            }
        }

    }
}
