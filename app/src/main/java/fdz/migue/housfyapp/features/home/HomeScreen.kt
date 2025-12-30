package fdz.migue.housfyapp.features.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fdz.migue.housfyapp.features.profile.ProfileViewModel
import fdz.migue.housfyapp.features.tasks.TaskViewModel


@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val profile by profileViewModel.profile.collectAsState(initial = null)
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())

    val userName = profile?.name ?: "Usuario"
    val pendingTasks = tasks.count { !it.isDone }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HomeCard("¡Bienvenid@ a Housfy, $userName!")
        }
        item {
            HomeCard("Tienes $pendingTasks tareas pendientes")
        }
    }
}