package fdz.migue.housfyapp.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.ClearDataRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: ClearDataRepository
) : ViewModel() {

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
        }
    }
}