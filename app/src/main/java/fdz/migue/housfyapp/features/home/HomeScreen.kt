package fdz.migue.housfyapp.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.features.activities.CalendarEventViewModel
import fdz.migue.housfyapp.features.profile.ProfileViewModel
import fdz.migue.housfyapp.features.tasks.TaskViewModel
import fdz.migue.housfyapp.ui.components.RoundedBackground
import java.time.LocalDate


@Composable
fun HomeScreen(
    calendarEventViewModel: CalendarEventViewModel,
    profileViewModel: ProfileViewModel,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val events by calendarEventViewModel.allEvents.collectAsState(initial = emptyList())
    val profile by profileViewModel.profile.collectAsState(initial = null)
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())

    val today = LocalDate.now()
    val userName = profile?.name ?: stringResource(R.string.user_default_name)
    val pendingTasks = tasks.count { !it.isDone }

    val upcomingEvents = events
        .filter { it.date >= today }
        .sortedBy { it.date }
        .take(3)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HomeCard(stringResource(R.string.home_welcome) + userName + stringResource(R.string.home_welcome_final))
        }
        item {
            HomeCard(stringResource(R.string.home_task_summary)+ pendingTasks + stringResource(R.string.home_task_summary_final))
        }
        item {
            RoundedBackground(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.home_activities_summary_title), fontSize = 25.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    if (upcomingEvents.isEmpty()) {
                        Text(stringResource(R.string.home_activities_summary_no_activities), fontStyle = FontStyle.Italic, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        upcomingEvents.forEach { event ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceContainer,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "${event.date.monthValue}/${event.date.dayOfMonth} | ${event.title}",
                                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}