package fdz.migue.housfyapp.features.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val dataStore: LanguageDataStore
) : ViewModel() {

    val language = dataStore.languageFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, "es")

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            dataStore.setLanguage(lang)
        }
    }
}