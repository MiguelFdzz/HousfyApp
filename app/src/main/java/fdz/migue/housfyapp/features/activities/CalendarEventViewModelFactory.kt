package fdz.migue.housfyapp.features.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fdz.migue.housfyapp.dao.activities.CalendarEventRepository

class CalendarEventViewModelFactory(private val repository: CalendarEventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarEventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}