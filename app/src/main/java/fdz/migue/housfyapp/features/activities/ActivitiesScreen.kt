package fdz.migue.housfyapp.features.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.dao.activities.CalendarEvent
import fdz.migue.housfyapp.features.activities.calendar.CalendarView
import fdz.migue.housfyapp.features.activities.event.CreateEventDialog
import fdz.migue.housfyapp.features.activities.event.EventCard
import fdz.migue.housfyapp.ui.components.RoundedBackground
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun ActivitiesScreen(
    viewModel: CalendarEventViewModel,
    modifier: Modifier = Modifier
) {
    val allEvents by viewModel.allEvents.collectAsState(initial = emptyList())
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showCreateEventDialog by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0]

    val dateFormatter = remember(currentLocale) {
        val pattern = when (currentLocale.language) {
            "es" -> "d 'de' MMMM"
            else -> "MMMM d"
        }
        DateTimeFormatter.ofPattern(pattern, currentLocale)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RoundedBackground(modifier = Modifier.padding(bottom = 16.dp)) {
                Column {
                    Text(
                        stringResource(R.string.activities_title),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CalendarView(
                        currentMonth = currentMonth,
                        onMonthChange = { currentMonth = it },
                        selectedDate = selectedDate,
                        onDateSelected = { date ->
                            selectedDate = date
                        },
                        events = allEvents
                    )
                }
            }
        }

        selectedDate?.let { date ->
            val dayEvents = allEvents.filter { it.date == date }

            item {
                RoundedBackground(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val formattedDate = date.format(dateFormatter)
                            Text(
                                stringResource(R.string.activities_events_at) + formattedDate,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = { showCreateEventDialog = true }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Añadir evento",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        if (dayEvents.isEmpty()) {
                            Text(
                                stringResource(R.string.activities_no_events),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            )
                        } else {
                            dayEvents.forEach { event ->
                                EventCard(
                                    event = event,
                                    onDelete = { viewModel.deleteEvent(event) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateEventDialog && selectedDate != null) {
        CreateEventDialog(
            date = selectedDate!!,
            onDismiss = { showCreateEventDialog = false },
            onSave = { title, description ->
                viewModel.saveEvent(
                    CalendarEvent(
                        date = selectedDate!!,
                        title = title,
                        description = description
                    )
                )
                showCreateEventDialog = false
            }
        )
    }
}