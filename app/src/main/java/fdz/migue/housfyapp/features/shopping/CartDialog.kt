package fdz.migue.housfyapp.features.shopping

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import fdz.migue.housfyapp.R

@Composable
fun CreateCartDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var cartName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.shopping_new_cart)) },
        text = {
            OutlinedTextField(
                value = cartName,
                onValueChange = { cartName = it },
                label = { Text(stringResource(R.string.shopping_new_cart_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (cartName.isNotBlank()) onConfirm(cartName) },
                enabled = cartName.isNotBlank()
            ) {
                Text(stringResource(R.string.shopping_new_cart_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.shopping_new_cart_cancel))
            }
        }
    )
}