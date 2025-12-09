package fdz.migue.housfyapp.features.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {

    val tasks = remember {
        mutableStateListOf(
            Task(text = "Comprar comida"),
            Task(text = "Llamar al médico"),
            Task(text = "Estudiar Compose"),
            Task(text = "Limpiar la casa"),
            Task(text = "Leer un libro")
        )
    }

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
                        tasks.add(Task(text = "Nueva tarea ${tasks.size + 1}"))
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
                    onDelete = {
                        tasks.remove(task)
                    },
                    onTaskUpdated = { newText ->
                        val index = tasks.indexOf(task)
                        if (index != -1) {
                            tasks[index] = task.copy(text = newText)
                        }
                    }
                )
            }
        }
    }
}
