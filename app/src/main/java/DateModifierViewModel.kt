
import androidx.lifecycle.ViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class DateModifierViewModel : ViewModel() {
    var selectedMilli: Long? = null

    fun modifyDate(days: Int = 0, weeks: Int = 0, months: Int = 0, years: Int = 0): LocalDate? {

        var modifiedDate = Instant.ofEpochMilli(selectedMilli!!).atZone(ZoneId.systemDefault()).toLocalDate()

        modifiedDate = modifiedDate!!.plusDays(days.toLong())
        modifiedDate = modifiedDate.plusWeeks(weeks.toLong())
        modifiedDate = modifiedDate.plusMonths(months.toLong())
        modifiedDate = modifiedDate.plusYears(years.toLong())

        return modifiedDate
    }
}
