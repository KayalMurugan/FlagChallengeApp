package com.app.flagschallengeapp.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.flagschallengeapp.ChallengeViewModel
import com.app.flagschallengeapp.R
import com.app.flagschallengeapp.ui.theme.CardBG
import com.app.flagschallengeapp.ui.theme.GreyColor
import com.app.flagschallengeapp.ui.theme.PrimaryColor
import com.app.flagschallengeapp.ui.theme.SecondaryColor
import kotlinx.coroutines.delay
import android.graphics.Paint as AndroidPaint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlagsChallengeScheduleScreen(viewModel: ChallengeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val timeSet by viewModel.timeSet.collectAsState()
    /*LaunchedEffect(Unit) {
        while (true) {
            viewModel.updateRemainingTimeFromTarget()
            delay(1000L)
        }
    }*/
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBG.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()

        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {

                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .width(90.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle),
                            contentDescription = "Icon",
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = String.format("00:%02d", uiState.currentTime),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        StrokedText(
                            text = "FLAGS CHALLENGE",
                            fontSize = 18.sp,
                            textColor = SecondaryColor,
                            strokeColor = Color.Black,
                            strokeSize = 3f,
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black.copy(alpha = 0.1f))
                )


               /* if(timeSet != true) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CHALLENGE",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.schedule),
                                    contentDescription = "Icon",
                                    modifier = Modifier
                                        .height(22.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }


                            Spacer(modifier = Modifier.height(16.dp))

                            TimeInputRow(viewModel)

                            Spacer(modifier = Modifier.height(20.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.setTime(true)
                                    },
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .height(40.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PrimaryColor,
                                        contentColor = Color.White
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 4.dp,
                                        pressedElevation = 6.dp
                                    )
                                ) {
                                    Text(
                                        text = "Save",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(40.dp))

                                Text(
                                    text = "WILL START IN",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = viewModel.formattedTime,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = GreyColor,
                                )
                            }
                        }
                    }*/

                    if (uiState.isFinished) {
                        QuizFinishScreen(viewModel = viewModel)
                    } else {
                        QuizQuestionScreen(viewModel = viewModel)
                    }



                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

}


@Composable
fun StrokedText(
    text: String,
    fontSize: TextUnit = 20.sp,
    textColor: Color = Color.Black,
    strokeColor: Color = Color.White,
    strokeSize: Float = 4f,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = fontSize.toPx()
                style = AndroidPaint.Style.STROKE
                color = strokeColor.toArgb()
                strokeWidth = strokeSize
                textAlign = AndroidPaint.Align.CENTER
            }

            val fillPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = fontSize.toPx()
                style = AndroidPaint.Style.FILL
                color = textColor.toArgb()
                textAlign = AndroidPaint.Align.CENTER
            }

            // Draw stroke first
            canvas.nativeCanvas.drawText(text, 0f, fontSize.toPx(), paint)
            // Draw fill on top
            canvas.nativeCanvas.drawText(text, 0f, fontSize.toPx(), fillPaint)
        }
    }
}


@Composable
fun TimeInputRow(viewModel: ChallengeViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeInputBox(
            label = "Hour",
            value1 = viewModel.hours1,
            value2 = viewModel.hours2,
            onValue1Change = { viewModel.hours1 = it },
            onValue2Change = { viewModel.hours2 = it }
        )
        TimeInputBox(
            label = "Minute",
            value1 = viewModel.minutes1,
            value2 = viewModel.minutes2,
            onValue1Change = { viewModel.minutes1 = it },
            onValue2Change = { viewModel.minutes2 = it }
        )
        TimeInputBox(
            label = "Second",
            value1 = viewModel.seconds1,
            value2 = viewModel.seconds2,
            onValue1Change = { viewModel.seconds1 = it },
            onValue2Change = { viewModel.seconds2 = it }
        )
    }
}


@Composable
fun TimeInputBox(
    label: String,
    value1: String,
    value2: String,
    onValue1Change: (String) -> Unit,
    onValue2Change: (String) -> Unit
) {
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

        Row {
            TextField(
                value = value1,
                onValueChange = {
                    if (it.length <= 1 && it.all { ch -> ch.isDigit() }) {
                        onValue1Change(it)       // update ViewModel
                        if (it.isNotEmpty()) {
                            focusRequester2.requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .focusRequester(focusRequester1),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(6.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(
                        text = "0",
                        modifier = Modifier.fillMaxSize(),
                        fontSize = 16.sp,
                        color = GreyColor,
                        textAlign = TextAlign.Center
                    )
                }
            )

            Spacer(modifier = Modifier.width(4.dp))

            TextField(
                value = value2,
                onValueChange = {
                    if (it.length <= 1 && it.all { ch -> ch.isDigit() }) {
                        onValue2Change(it)       // update ViewModel
                        if (it.isEmpty()) {
                            focusRequester1.requestFocus()
                        } else {
                            focusManager.clearFocus()
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .focusRequester(focusRequester2),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFE0E0E0),
                    unfocusedContainerColor = Color(0xFFE0E0E0),
                    disabledContainerColor = Color(0xFFE0E0E0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(6.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(
                        text = "0",
                        modifier = Modifier.fillMaxSize(),
                        fontSize = 12.sp,
                        color = GreyColor,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}



