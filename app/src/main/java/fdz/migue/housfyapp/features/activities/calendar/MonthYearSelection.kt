package fdz.migue.housfyapp.features.activities.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun MonthYearSelector(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
        }

        Text(
            text = currentMonth.toLocalizedString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
        }
    }
}

@Composable
fun YearMonth.toLocalizedString(): String {
    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0]

    val formatter = remember(currentLocale) {
        DateTimeFormatter.ofPattern("MMMM yyyy", currentLocale)
    }

    return this.format(formatter).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(currentLocale) else it.toString()
    }
}