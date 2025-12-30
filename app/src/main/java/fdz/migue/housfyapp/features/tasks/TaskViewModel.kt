package fdz.migue.housfyapp.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.tasks.Task
import fdz.migue.housfyapp.dao.tasks.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val tasks: Flow<List<Task>> = taskRepository.getTasks()

    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}