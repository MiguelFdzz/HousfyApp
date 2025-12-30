package fdz.migue.housfyapp.features.activities


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.activities.CalendarEvent
import fdz.migue.housfyapp.dao.activities.CalendarEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarEventViewModel(private val repository: CalendarEventRepository) : ViewModel() {

    val allEvents: Flow<List<CalendarEvent>> = repository.getAllEvents()

    fun getEventsByDate(date: LocalDate): Flow<List<CalendarEvent>> {
        return repository.getEventsByDate(date)
    }

    fun getEventsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<CalendarEvent>> {
        return repository.getEventsBetweenDates(startDate, endDate)
    }

    fun saveEvent(event: CalendarEvent) {
        viewModelScope.launch {
            repository.saveEvent(event)
        }
    }

    fun deleteEvent(event: CalendarEvent) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    fun deleteEventById(eventId: String) {
        viewModelScope.launch {
            repository.deleteEventById(eventId)
        }
    }
}