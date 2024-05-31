
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class DateCalculatorViewModel : ViewModel() {
    var startMilli: Long? = null
    var endMilli: Long? = null

    fun calculateDateDifference(): DateDifference {
        if (startMilli == null || endMilli == null) {
            return DateDifference(0, 0, 0, 0)
        }

        val startDate = Instant.ofEpochMilli(startMilli!!).atZone(ZoneId.systemDefault()).toLocalDate()
        val endDate = Instant.ofEpochMilli(endMilli!!).atZone(ZoneId.systemDefault()).toLocalDate()

        //val days = ChronoUnit.DAYS.between(startDate, endDate).toInt()
        val days = startDate.until(endDate).days % 7
        val weeks = (ChronoUnit.WEEKS.between(startDate, endDate) % 4).toInt()
        //val months = ChronoUnit.MONTHS.between(startDate, endDate)
        val months = startDate.until(endDate).months
        //val years = ChronoUnit.YEARS.between(startDate, endDate)
        val years = startDate.until(endDate).years

        return DateDifference(days, weeks, months, years)
    }
}

data class DateDifference(
    val days: Int,
    val weeks: Int,
    val months: Int,
    val years: Int
)
