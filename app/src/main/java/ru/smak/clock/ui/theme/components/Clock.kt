package ru.smak.clock.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.smak.clock.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Clock(
    hoursAngle: Float,
    minutesAngle: Float,
    secondsAngle: Float,
    modifier: Modifier = Modifier,
    name: String? = null,
){
    Surface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.aspectRatio(1f),
                contentAlignment = Alignment.Center,
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize(0.97f)
                        .shadow(2.dp, shape = CircleShape),
                    contentDescription = stringResource(R.string.clock)
                ) {
                    val centerSize = size.minDimension / 50f
                    val labelMarkSize = size.minDimension / 15f
                    val hArrowWidth = labelMarkSize / 2.5f
                    val mArrowWidth = hArrowWidth / 1.5f
                    val sArrowWidth = hArrowWidth / 3f
                    drawCircle(
                        Brush.linearGradient(listOf(Color.LightGray, Color.White))
                    )
                    drawCircle(
                        Color.Black, centerSize,
                    )
                    repeat(12) {
                        rotate(it * 30f) {
                            drawLine(
                                Color.Red,
                                Offset(center.x, 0f),
                                Offset(center.x, labelMarkSize),
                                4.dp.toPx(),
                                StrokeCap.Round,
                            )
                        }
                    }
                    rotate(hoursAngle) {
                        drawLine(
                            Color.Blue,
                            Offset(center.x, center.y + labelMarkSize),
                            Offset(center.x, center.y / 2f),
                            strokeWidth = hArrowWidth,
                            StrokeCap.Square,
                        )
                    }
                    rotate(minutesAngle) {
                        drawLine(
                            Color.Blue,
                            Offset(center.x, center.y + labelMarkSize),
                            Offset(center.x, labelMarkSize + 2f * centerSize),
                            strokeWidth = mArrowWidth,
                            StrokeCap.Square,
                        )
                    }
                    rotate(secondsAngle) {
                        drawLine(
                            Color.Black,
                            Offset(center.x, center.y + labelMarkSize),
                            Offset(center.x, 2f * centerSize),
                            strokeWidth = sArrowWidth,
                            StrokeCap.Square,
                        )
                    }
                }
            }
            name?.let{ Text(it) }
        }
    }

}


@Preview
@Composable
fun ClockPreview(){
    Clock(
        hoursAngle = 90f,
        minutesAngle = 0f,
        secondsAngle = 180f,
        Modifier.fillMaxWidth(),
        "Москва"
    )
}