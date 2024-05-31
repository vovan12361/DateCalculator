
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datecalculator.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("WeekBasedYear")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateModifierScreen() {
    val viewModel = remember { DateModifierViewModel() }
    var daysToAdd by remember { mutableStateOf(TextFieldValue()) }
    var weeksToAdd by remember { mutableStateOf(TextFieldValue()) }
    var monthsToAdd by remember { mutableStateOf(TextFieldValue()) }
    var yearsToAdd by remember { mutableStateOf(TextFieldValue()) }
    var showDialog by remember { mutableStateOf(false) }
    var modifiedDate by remember { mutableStateOf(LocalDate.now())}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis(),initialDisplayMode = DisplayMode.Input)
        DatePicker(state = state)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = daysToAdd,
            onValueChange = { daysToAdd = it },
            label = { Text(stringResource(R.string.days_to_add_subtract)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true

        )
        OutlinedTextField(
            value = weeksToAdd,
            onValueChange = { weeksToAdd = it },
            label = { Text(stringResource(R.string.weeks_to_add_subtract)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true

        )
        OutlinedTextField(
            value = monthsToAdd,
            onValueChange = { monthsToAdd = it },
            label = { Text(stringResource(R.string.months_to_add_subtract))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        OutlinedTextField(
            value = yearsToAdd,
            onValueChange = { yearsToAdd = it },
            label = { Text(stringResource(R.string.years_to_add_subtract)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.selectedMilli = state.selectedDateMillis
            modifiedDate = viewModel.modifyDate(
                daysToAdd.text.toIntOrNull() ?: 0,
                weeksToAdd.text.toIntOrNull() ?: 0,
                monthsToAdd.text.toIntOrNull() ?: 0,
                yearsToAdd.text.toIntOrNull() ?: 0
            )
            showDialog = true
        }) {
            Text(stringResource(R.string.modify_date))
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false },
                title = { Text(stringResource(R.string.result_date)) },
                text = {
                    if (modifiedDate != null) {
                        Text(
                            stringResource(
                                R.string.your_start_date_modified_date,
                                Instant.ofEpochMilli(state.selectedDateMillis!!).atZone(
                                    ZoneId.systemDefault()
                                ).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                                modifiedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            )
                        )
                    } else {
                        Text(
                            stringResource(
                                R.string.your_start_date_modified_date_n_a,
                                Instant.ofEpochMilli(state.selectedDateMillis!!).atZone(
                                    ZoneId.systemDefault()
                                ).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            )
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateModifierScreen() {
    MaterialTheme {
        DateModifierScreen()
    }
}
