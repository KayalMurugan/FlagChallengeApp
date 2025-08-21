package com.app.flagschallengeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.flagschallengeapp.ChallengeViewModel
import com.app.flagschallengeapp.R
import com.app.flagschallengeapp.data.model.Option
import com.app.flagschallengeapp.data.model.OptionState
import com.app.flagschallengeapp.ui.theme.DarkGreyColor
import com.app.flagschallengeapp.ui.theme.GreyColor
import com.app.flagschallengeapp.ui.theme.LightGreen
import com.app.flagschallengeapp.ui.theme.PrimaryColor
import com.app.flagschallengeapp.util.FLAG_HEIGHT
import com.app.flagschallengeapp.util.FLAG_WIDTH
import com.app.flagschallengeapp.util.OPTION_HEIGHT
import com.app.flagschallengeapp.util.OPTION_WIDTH
import com.app.flagschallengeapp.util.SPACER_WIDTH
import com.app.flagschallengeapp.util.TEXT_PADDING_HORIZONTAL
import com.app.flagschallengeapp.util.TEXT_PADDING_VERTICAL


@Composable
fun QuizQuestionScreen(
    viewModel: ChallengeViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val question = viewModel.currentQuestion

    question?.let { question ->

        val options = question.countries.map { country ->

            when {
                state.isShowingAnswer && country.id == question.answer_id ->
                    Option(
                        country.country_name,
                        country.id,
                        question.answer_id,
                        OptionState.CORRECT
                    )

                state.isShowingAnswer && state.selectedAnswerId == country.id ->
                    Option(country.country_name, country.id, question.answer_id, OptionState.WRONG)

                else -> Option(country.country_name, country.id, question.answer_id)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            QuestionHeader(
                questionNumber = state.currentQuestionIndex + 1,
                questionText = "GUESS THE COUNTRY FROM THE FLAG ?"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FlagImage(question.country_code, Modifier.size(FLAG_WIDTH, FLAG_HEIGHT))

                OptionsGrid(options = options, viewModel = viewModel)
            }
        }
    }


}

@Composable
fun QuestionHeader(questionNumber: Int, questionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 43.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
            Text(
                text = "$questionNumber",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .background(PrimaryColor, shape = CircleShape)
                    .padding(horizontal = TEXT_PADDING_HORIZONTAL, vertical = TEXT_PADDING_VERTICAL)
            )
        }

        Spacer(modifier = Modifier.width(45.dp))

        Text(
            text = questionText,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OptionsGrid(
    options: List<Option>, viewModel: ChallengeViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.chunked(2).forEach { rowOptions ->
            Row(horizontalArrangement = Arrangement.spacedBy(SPACER_WIDTH)) {
                rowOptions.forEach { option ->

                    OptionButton(
                        text = option.text,
                        id = option.id,
                        state = option.state,
                        viewModel = viewModel,
                        onClick = {
                            if (!state.isShowingAnswer) {
                                viewModel.selectAnswer(answerId = option.id, isCorrect = option.ansId == option.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    id: Int,
    state: OptionState = OptionState.DEFAULT,
    viewModel: ChallengeViewModel,
    onClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    var state = state

    if (!uiState.isShowingAnswer && id == uiState.selectedAnswerId) {
        state = OptionState.SELECT
    }

    val backgroundColor = when (state) {
        OptionState.CORRECT -> Color.Transparent
        OptionState.WRONG -> PrimaryColor
        OptionState.DEFAULT -> Color.Transparent
        OptionState.SELECT -> GreyColor
    }
    val borderColor = when (state) {
        OptionState.CORRECT -> LightGreen
        OptionState.WRONG -> PrimaryColor
        OptionState.DEFAULT -> DarkGreyColor
        OptionState.SELECT -> GreyColor
    }
    val textColor = when (state) {
        OptionState.CORRECT -> LightGreen
        OptionState.WRONG -> Color.White
        OptionState.DEFAULT -> DarkGreyColor
        OptionState.SELECT -> Color.White
    }

    val stateText = when (state) {
        OptionState.CORRECT -> "CORRECT"
        OptionState.WRONG -> "WRONG"
        OptionState.DEFAULT -> ""
        OptionState.SELECT -> ""
    }

    Column {
        Box(
            modifier = Modifier
                .width(OPTION_WIDTH)
                .height(OPTION_HEIGHT)
                .clip(RoundedCornerShape(6.dp))
                .background(backgroundColor)
                .border(1.dp, borderColor, RoundedCornerShape(6.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            )
        }

        if (state == OptionState.CORRECT || state == OptionState.WRONG)
            Box(
                modifier = Modifier
                    .width(OPTION_WIDTH),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stateText,
                    color = borderColor,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
    }

}

@Composable
fun FlagImage(code: String, modifier: Modifier = Modifier) {
    val url = "https://flagcdn.com/w320/${code.lowercase()}.png"
    AsyncImage(
        model = url,
        contentDescription = "Flag of $code",
        modifier = modifier
            .width(70.dp)
            .height(50.dp)
    )
}



