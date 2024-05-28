package ru.smak.clock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.smak.clock.ui.theme.components.Clock

@Composable
fun ClockList(
    hourAngle: Float,
    mimuteAngle: Float,
    secondAngle: Float,
    clockList: List<Pair<String, ArrowAngles>>,
    modifier: Modifier = Modifier,
){
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(150.dp),
    ) {
        items(clockList){
            Clock(
                hoursAngle = hourAngle + it.second.hourAngle,
                minutesAngle = mimuteAngle + it.second.minuteAngle,
                secondsAngle = secondAngle,
                modifier = Modifier.fillMaxSize(),
                it.first,
            )
        }
    }
}

@Composable
fun AddClockDialog(
    modifier: Modifier = Modifier,
    onDismiss: ()->Unit = {},
    onConfirm: (String, Int, Int)->Unit = {_, _, _ ->}
){
    var name by remember{ mutableStateOf("Новый часовой пояс") }
    var hours by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirm(name, hours.toInt(), minutes.toInt()) }) {
                Text("Добавить")
            }
        },
        modifier = modifier,
        title = { Text(stringResource(R.string.dialog_title)) },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Название часового пояса") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = hours, onValueChange = { hours = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Часы (+ или -)") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = minutes, onValueChange = { minutes = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Минуты (+ или -)") })
            }
        }
    )
}


@Preview
@Composable
fun AddClockDialogPreview(){
    AddClockDialog()
}