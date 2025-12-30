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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fdz.migue.housfyapp.dao.shopping.ShoppingCart
import fdz.migue.housfyapp.ui.components.RoundedBackground
import java.util.UUID

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
                "¡Bienvenid@ a la lista de la compra",
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showCreateDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Crear nuevo carrito")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (carts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay carritos aún.\n¡Crea tu primer carrito!",
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