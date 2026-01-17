package fdz.migue.housfyapp.features.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.dao.shopping.ShoppingCart

@Composable
fun CartEditorScreen(
    cart: ShoppingCart,
    onBack: () -> Unit,
    onSave: (ShoppingCart) -> Unit
) {
    var content by remember { mutableStateOf(cart.content) }
    var isPreviewMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Barra superior
        Surface(
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onSave(cart.copy(content = content))
                    onBack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text(
                    cart.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isPreviewMode = !isPreviewMode }) {
                    Icon(
                        if (isPreviewMode) Icons.Default.Edit else Icons.Default.Visibility,
                        contentDescription = if (isPreviewMode) "Editar" else "Vista previa"
                    )
                }
            }
        }

        if (isPreviewMode) {
            // Vista previa Markdown
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    MarkdownPreview(content)
                }
            }
        } else {
            // Editor
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                placeholder = {
                    Column() {
                        Text(stringResource(R.string.shopping_cart_placeholder_1))
                        Text(stringResource(R.string.shopping_cart_placeholder_2))
                        Text(stringResource(R.string.shopping_cart_placeholder_3))
                        Text(stringResource(R.string.shopping_cart_placeholder_4))
                        Text(stringResource(R.string.shopping_cart_placeholder_5))
                        Text(stringResource(R.string.shopping_cart_placeholder_6))
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }
}