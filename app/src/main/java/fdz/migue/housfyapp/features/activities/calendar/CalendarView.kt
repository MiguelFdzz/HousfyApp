package fdz.migue.housfyapp.features.activities.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fdz.migue.housfyapp.dao.activities.CalendarEvent
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarView(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    events: List<CalendarEvent>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MonthYearSelector(
            currentMonth = currentMonth,
            onMonthChange = onMonthChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("L", "M", "X", "J", "V", "S", "D").forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val firstDayOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value
        val daysInMonth = currentMonth.lengthOfMonth()

        val weeks = ((firstDayOfWeek + daysInMonth - 1) / 7) + 1

        Column {
            repeat(weeks) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { dayOfWeek ->
                        val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 2
                        if (dayNumber in 1..daysInMonth) {
                            val date = currentMonth.atDay(dayNumber)
                            val eventCount = events.count { it.date == date }
                            DayCell(
                                day = dayNumber,
                                isSelected = date == selectedDate,
                                isToday = date == LocalDate.now(),
                                eventCount = eventCount,
                                onClick = { onDateSelected(date) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
