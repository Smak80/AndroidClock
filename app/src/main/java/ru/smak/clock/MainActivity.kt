package ru.smak.clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.smak.clock.ui.theme.ClockTheme
import ru.smak.clock.ui.theme.components.Clock
import java.time.ZonedDateTime

class MainActivity : ComponentActivity() {

    private val mvm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClockTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { mvm.showDialog = true }) {
                            Text("Добавить", modifier = Modifier.padding(8.dp))
                        }
                    }
                ) { innerPadding ->
                    if (mvm.showDialog){
                        AddClockDialog(onDismiss = {
                            mvm.showDialog = false
                        }){ name, h, m ->
                            mvm.showDialog = false
                            mvm.addWatch(name, h, m)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ClockList(
                            mvm.hours,
                            mvm.minutes,
                            mvm.seconds,
                            mvm.clockList,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

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
                OutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth(), placeholder = {Text("Название часового пояса")})
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = hours, onValueChange = { hours = it }, modifier = Modifier.fillMaxWidth(), placeholder = {Text("Часы (+ или -)")})
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = minutes, onValueChange = { minutes = it }, modifier = Modifier.fillMaxWidth(), placeholder = {Text("Минуты (+ или -)")})
            }
        }
    )
}

@Preview
@Composable
fun AddClockDialogPreview(){
    AddClockDialog()
}