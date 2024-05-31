import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DateCalculatorViewModel : ViewModel() {
    var startDate: LocalDate? = null
    var endDate: LocalDate? = null

    fun calculateDateDifference(): DateDifference {
        if (startDate == null || endDate == null) {
            return DateDifference(0, 0, 0, 0)
        }

        val days = ChronoUnit.DAYS.between(startDate, endDate).toInt()
        val weeks = ChronoUnit.WEEKS.between(startDate, endDate).toInt()
        val months = ChronoUnit.MONTHS.between(startDate, endDate).toInt()
        val years = ChronoUnit.YEARS.between(startDate, endDate).toInt()

        return DateDifference(days, weeks, months, years)
    }

    fun setTodayAsStartDate() {
        startDate = LocalDate.now()
    }
}

data class DateDifference(
    val days: Int,
    val weeks: Int,
    val months: Int,
    val years: Int
)
