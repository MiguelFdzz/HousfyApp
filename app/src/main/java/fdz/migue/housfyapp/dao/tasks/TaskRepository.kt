package fdz.migue.housfyapp.dao.tasks

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

}