package com.app.flagschallengeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextDecoration
import com.app.flagschallengeapp.ui.theme.FlagsChallengeAppTheme
import com.app.flagschallengeapp.ui.theme.PrimaryColor
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.lifecycleScope
import com.app.flagschallengeapp.ui.screens.FlagsChallengeScheduleScreen
import com.app.flagschallengeapp.ui.theme.SecondaryColor
import kotlinx.coroutines.launch
import android.graphics.Paint as AndroidPaint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ChallengeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            FlagsChallengeAppTheme {
                FlagsChallengeScreen()
            }
        }
    }
    override fun onStop() {
        super.onStop()
        viewModel.leftQuiz()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.resumeQuiz()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagsChallengeScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PrimaryColor,
                ),
                title = {  },
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .navigationBarsPadding()
            ) {
                FlagsChallengeScheduleScreen()
            }
        }
    )

}




