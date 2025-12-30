package fdz.migue.housfyapp.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fdz.migue.housfyapp.dao.tasks.TaskRepository

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}