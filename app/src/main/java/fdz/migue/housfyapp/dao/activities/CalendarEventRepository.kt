package fdz.migue.housfyapp.dao.activities


import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface CalendarEventRepository {
    fun getAllEvents(): Flow<List<CalendarEvent>>
    fun getEventsByDate(date: LocalDate): Flow<List<CalendarEvent>>
    fun getEventsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<CalendarEvent>>
    suspend fun saveEvent(event: CalendarEvent)
    suspend fun deleteEvent(event: CalendarEvent)
    suspend fun deleteEventById(eventId: String)
}

class CalendarEventRepositoryImpl(private val calendarEventDao: CalendarEventDao) : CalendarEventRepository {

    override fun getAllEvents(): Flow<List<CalendarEvent>> {
        return calendarEventDao.getAllEvents()
    }

    override fun getEventsByDate(date: LocalDate): Flow<List<CalendarEvent>> {
        return calendarEventDao.getEventsByDate(date)
    }

    override fun getEventsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<CalendarEvent>> {
        return calendarEventDao.getEventsBetweenDates(startDate, endDate)
    }

    override suspend fun saveEvent(event: CalendarEvent) {
        calendarEventDao.upsertEvent(event)
    }

    override suspend fun deleteEvent(event: CalendarEvent) {
        calendarEventDao.deleteEvent(event)
    }

    override suspend fun deleteEventById(eventId: String) {
        calendarEventDao.deleteEventById(eventId)
    }
}