package fdz.migue.housfyapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fdz.migue.housfyapp.dao.activities.CalendarEvent
import fdz.migue.housfyapp.dao.activities.CalendarEventDao
import fdz.migue.housfyapp.dao.chat.ChatDAO
import fdz.migue.housfyapp.dao.chat.ChatMessage
import fdz.migue.housfyapp.dao.profile.Profile
import fdz.migue.housfyapp.dao.profile.ProfileDao
import fdz.migue.housfyapp.dao.shopping.ShoppingCart
import fdz.migue.housfyapp.dao.shopping.ShoppingDao
import fdz.migue.housfyapp.dao.tasks.Task
import fdz.migue.housfyapp.dao.tasks.TaskDao

@Database(
    entities = [Task::class, Profile::class, ShoppingCart::class, CalendarEvent::class, ChatMessage::class],
    version = 5
)
@TypeConverters(Converters::class)
abstract class HousfyDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun profileDao(): ProfileDao
    abstract fun shoppingDao(): ShoppingDao
    abstract fun calendarEventDao(): CalendarEventDao
    abstract fun chatDao(): ChatDAO

    companion object {
        @Volatile
        private var INSTANCE: HousfyDatabase? = null

        fun getDatabase(context: Context): HousfyDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HousfyDatabase::class.java,
                    "housfy_database"
                ).build()
                    .also { INSTANCE = it }
                instance
            }
        }
    }
}