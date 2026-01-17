@file:Suppress("DEPRECATION")

package fdz.migue.housfyapp.features.language

import android.content.Context
import java.util.Locale

fun Context.updateLocale(language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = resources.configuration
    config.setLocale(locale)

    return createConfigurationContext(config)
}