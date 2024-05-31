
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datecalculator.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalculatorScreen(navController: NavController) {
    val viewModel = remember { DateCalculatorViewModel() }
    var showDialog by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(DateDifference(0, 0, 0, 0)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Input,
            initialSelectedStartDateMillis = System.currentTimeMillis(),
            initialSelectedEndDateMillis = System.currentTimeMillis())
        DateRangePicker(state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = {
                Text(stringResource(R.string.timeInterval), modifier = Modifier.padding(16.dp))
            },
            headline = {
                Row(modifier = Modifier
                    .fillMaxWidth()) {
                    Box(Modifier.weight(1f)){
                        state.selectedStartDateMillis?.let { Instant.ofEpochMilli(it).atZone(
                            ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(
                            FormatStyle.SHORT)).toString().let {Text(text = it)} }
                    }
                    Box(Modifier.weight(1f)){
                        state.selectedEndDateMillis?.let { Instant.ofEpochMilli(it).atZone(
                            ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(
                            FormatStyle.SHORT)).toString().let {Text(text = it)} }
                    }
                }
            },
            showModeToggle = true)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            state.setSelection(System.currentTimeMillis(), state.selectedEndDateMillis)
        }) {
            Text(stringResource(R.string.set_today_as_start_date))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.startMilli = state.selectedStartDateMillis
            viewModel.endMilli = state.selectedEndDateMillis
            result = viewModel.calculateDateDifference()
            showDialog = true
        }) {
            Text(stringResource(R.string.calculate_difference))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.date_difference)) },
            text = {
                Text(
                    stringResource(
                        R.string.days_weeks_months_years,
                        result.days,
                        result.weeks,
                        result.months,
                        result.years
                    ))
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}