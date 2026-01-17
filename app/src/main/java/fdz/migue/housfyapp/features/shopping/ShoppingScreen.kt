package fdz.migue.housfyapp.features.shopping

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fdz.migue.housfyapp.R
import fdz.migue.housfyapp.ui.components.RoundedBackground

@Composable
fun ShoppingScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingViewModel
) {
    val carts by viewModel.carts.collectAsStateWithLifecycle()
    val selectedCart by viewModel.selectedCart.collectAsStateWithLifecycle()

    var showCreateDialog by remember { mutableStateOf(false) }

    if (selectedCart != null) {
        CartEditorScreen(
            cart = selectedCart!!,
            onBack = { viewModel.clearSelection() },
            onSave = { updatedCart ->
                viewModel.updateCart(updatedCart)
                viewModel.clearSelection()
            }
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RoundedBackground {
            Text(
                stringResource(R.string.shopping_title),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showCreateDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.shopping_new_cart))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (carts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.shopping_no_carts_1),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = carts,
                    key = { it.id }
                ) { cart ->
                    SwipeToDeleteCart(
                        cart = cart,
                        onDelete = { viewModel.deleteCart(cart) },
                        onClick = { viewModel.selectCart(cart) }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateCartDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { name ->
                viewModel.createCart(name)
                showCreateDialog = false
            }
        )
    }
}