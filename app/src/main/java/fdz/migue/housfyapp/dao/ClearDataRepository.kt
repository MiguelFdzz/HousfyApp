package fdz.migue.housfyapp.dao

class ClearDataRepository(
    private val database: HousfyDatabase
) {
    suspend fun clearAllData() {
        database.profileDao().deleteAll()
        database.taskDao().deleteAll()
        database.shoppingDao().deleteAll()
        database.calendarEventDao().deleteAll()
        database.chatDao().deleteAllMessages()
    }
}