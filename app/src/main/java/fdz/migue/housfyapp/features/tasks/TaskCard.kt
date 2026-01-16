package fdz.migue.housfyapp.features.tasks

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TaskCard(
    text: String,
    onDelete: () -> Unit,
    isDone: Boolean,
    onToggleDone: () -> Unit,
    onTaskUpdated: (String) -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedText by rememberSaveable(text) { mutableStateOf(text) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var offsetX by remember { mutableStateOf(0f) }
    var isDeleting by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    // Función para finalizar la edición
    fun finishEditing() {
        isEditing = false
        focusManager.clearFocus()
        if (editedText.trim() != text.trim() && editedText.isNotBlank()) {
            onTaskUpdated(editedText.trim())
        } else {
            editedText = text
        }
    }

    // Sincronizar editedText con text cuando cambie desde fuera
    LaunchedEffect(text) {
        if (!isEditing) {
            editedText = text
        }
    }

    // Animamos el color de fondo
    val backgroundColor by animateColorAsState(
        targetValue = if (isDone)
            MaterialTheme.colorScheme.surfaceVariant.copy()
        else
            MaterialTheme.colorScheme.surfaceBright,
        label = "backgroundColor"
    )

    // Animación de la línea
    val lineProgress by animateFloatAsState(
        targetValue = if (isDone) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "lineProgress"
    )

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "offsetX"
    )

    LaunchedEffect(isEditing) {
        if (isEditing) {
            textFieldValue = TextFieldValue(
                text = editedText,
                selection = TextRange(editedText.length)
            )
            delay(50)
            focusRequester.requestFocus()
        }
    }

    // Si está en proceso de eliminación, no renderizar nada más
    if (isDeleting) {
        return
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            if (isEditing) return@detectDragGestures
                        },
                        onDragEnd = {
                            if (isEditing || isDeleting) return@detectDragGestures

                            coroutineScope.launch {
                                when {
                                    // Derecha Eliminar
                                    offsetX > 200f -> {
                                        isDeleting = true
                                        delay(50)
                                        onDelete()
                                    }
                                    // Izquierda Editar
                                    offsetX < -200f -> {
                                        offsetX = 0f
                                        isEditing = true
                                    }
                                    // Vuelve a la posicion original si no hizo ninguna acción
                                    else -> {
                                        offsetX = 0f
                                    }
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (isEditing || isDeleting) return@detectDragGestures
                            // Solo hacer swipe horizontal
                            if (kotlin.math.abs(dragAmount.x) > kotlin.math.abs(dragAmount.y)) {
                                change.consume()
                                offsetX += dragAmount.x
                            }
                        }
                    )
                }
                .clickable(
                    enabled = !isEditing && !isDeleting,
                    onClick = { onToggleDone() }
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: Reordenar, es un infierno de hacer llevo intentandolo mucho tiempo, se deja para el final
                Icon(
                    imageVector = Icons.Default.DragHandle,
                    contentDescription = "Reordenar",
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (isEditing) {
                        TextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                textFieldValue = newValue
                                editedText = newValue.text
                            },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .focusRequester(focusRequester)
                                .onKeyEvent { event ->
                                    // Detectar Enter y tecla Done del teclado móvil
                                    if (event.key == Key.Enter && event.type == KeyEventType.KeyDown) {
                                        finishEditing()
                                        true
                                    } else false
                                },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    finishEditing()
                                }
                            )
                        )
                    } else {
                        Text(
                            text = editedText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (isDone)
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                    val drawLineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    Canvas(
                        modifier = Modifier.matchParentSize()
                    ) {
                        if (lineProgress > 0f) {
                            val y = size.height / 2
                            drawLine(
                                color = drawLineColor,
                                start = Offset(0f, y),
                                end = Offset(size.width * lineProgress, y),
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }
    }
}