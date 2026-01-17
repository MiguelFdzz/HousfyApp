package fdz.migue.housfyapp.features.language

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageDataStore(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("settings")

    val languageFlow: Flow<String> =
        context.dataStore.data.map {
            it[LanguagePreferences.LANGUAGE] ?: "es"
        }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit {
            it[LanguagePreferences.LANGUAGE] = lang
        }
    }
}
