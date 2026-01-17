package fdz.migue.housfyapp.features.activities.event

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fdz.migue.housfyapp.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun CreateEventDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val currentLocale = configuration.locales[0]

    val dateFormatter = remember(currentLocale) {
        val pattern = when (currentLocale.language) {
            "es" -> "d 'de' MMMM"
            else -> "MMMM d"
        }
        DateTimeFormatter.ofPattern(pattern, currentLocale)
    }
    val formattedDate = date.format(dateFormatter)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.activities_new_event_title) + formattedDate)
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.activities_new_activity_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.activities_new_activity_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onSave(title, description)
                    }
                },
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(R.string.activities_new_activity_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.activities_new_activity_cancel))
            }
        }
    )
}