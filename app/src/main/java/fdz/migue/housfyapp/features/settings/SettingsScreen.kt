package fdz.migue.housfyapp.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.features.language.LanguageViewModel
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun SettingsScreen(
    languageViewModel: LanguageViewModel,
    modifier: Modifier = Modifier,
    onClearData: () -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    val currentLang by languageViewModel.language.collectAsState()
    var selectedLanguage by remember(currentLang) {
        mutableStateOf(
            if (currentLang == "en") "English" else "Español"
        )
    }

    var showConfirmDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            RoundedBackground(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.settings_title),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            RoundedBackground(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.settings_language_text),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Box {
                            OutlinedButton(
                                onClick = { expanded = true },
                                modifier.width(100.dp)
                            ) {
                                Text(selectedLanguage)
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                listOf("Español", "English").forEach { language ->
                                    DropdownMenuItem(
                                        text = { Text(language) },
                                        onClick = {
                                            selectedLanguage = language
                                            expanded = false

                                            when (language) {
                                                "Español" -> languageViewModel.setLanguage("es")
                                                "English" -> languageViewModel.setLanguage("en")
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.settings_clear_data_text),
                            color = MaterialTheme.colorScheme.onError,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
    if (showConfirmDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(stringResource(R.string.settings_confirm_title))
            },
            text = {
                Text(stringResource(R.string.settings_confirm_text))
            },
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmDialog = false
                        onClearData()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(stringResource(R.string.settings_confirm_delete), color = MaterialTheme.colorScheme.onError)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text(stringResource(R.string.settings_confirm_cancel))
                }
            }
        )
    }
}