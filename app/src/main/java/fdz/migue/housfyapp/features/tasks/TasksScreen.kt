package fdz.migue.housfyapp.features.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.dao.tasks.Task
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel
) {
    val tasks = viewModel.tasks
        .collectAsState(initial = emptyList())
        .value

    RoundedBackground(
        modifier = modifier.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text(
                    "Lista de Tareas",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            item {
                Button(
                    onClick = {
                        viewModel.insertTask(
                            Task(text = "Nueva tarea")
                        )
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.8f)
                ) {
                    Text("➕ Crear Tarea")
                }
            }

            items(tasks, key = { it.id }) { task ->
                TaskCard(
                    text = task.text,
                    isDone = task.isDone,
                    onDelete = {
                        viewModel.deleteTask(task)
                    },
                    onTaskUpdated = { newText ->
                        viewModel.updateTask(
                            task.copy(text = newText)
                        )
                    },
                    onToggleDone = {
                        viewModel.updateTask(
                            task.copy(isDone = !task.isDone)
                        )
                    }
                )
            }
        }
    }
}