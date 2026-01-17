package fdz.migue.housfyapp.dao.activities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_events ORDER BY date ASC")
    fun getAllEvents(): Flow<List<CalendarEvent>>

    @Query("SELECT * FROM calendar_events WHERE date = :date ORDER BY title ASC")
    fun getEventsByDate(date: LocalDate): Flow<List<CalendarEvent>>

    @Query("SELECT * FROM calendar_events WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getEventsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<CalendarEvent>>

    @Upsert
    suspend fun upsertEvent(event: CalendarEvent)

    @Delete
    suspend fun deleteEvent(event: CalendarEvent)

    @Query("DELETE FROM calendar_events WHERE id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Query("DELETE FROM calendar_events")
    suspend fun deleteAll()
}