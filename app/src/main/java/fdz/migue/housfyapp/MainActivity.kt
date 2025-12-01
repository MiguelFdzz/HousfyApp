@file:OptIn(ExperimentalMaterial3Api::class)

package fdz.migue.housfyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID
import kotlin.math.roundToInt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PantallaPrincipal()
        }
    }
}

@Preview
@Composable
fun Preview(){
    PantallaPrincipal()
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier){
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onNavigate = { route ->
                        scope.launch { drawerState.close() }
                        navController.navigate(route)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(onOpenDrawer = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                })
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("profileedit") { PEditScreen() }

                composable("home") { HomeScreen() }
                composable("tasks") { TaskScreen() }
                composable("activities") { ActivitiesScreen() }
                composable("shopping") { ShoppingScreen() }
                composable("chat") { ChatScreen() }
                composable("conf") { SettingsScreen() }
            }
        }
    }
}

//----------------------------------------------------------------------------------------------------
//-------------------------------------------- Screens -----------------------------------------------
//----------------------------------------------------------------------------------------------------
@Composable
fun PEditScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            RoundedBackground{
                Text("¡Bienvenid@ al editor del perfil", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ChatScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            RoundedBackground{
                Text("¡Bienvenid@ al chat", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ShoppingScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            RoundedBackground{
                Text("¡Bienvenid@ a la lista de la compra", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ActivitiesScreen(modifier: Modifier = Modifier) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showCreateEventDialog by remember { mutableStateOf(false) }
    var events by remember { mutableStateOf<List<CalendarEvent>>(emptyList()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RoundedBackground(modifier = Modifier.padding(bottom = 16.dp)) {
                Column {
                    Text(
                        "Actividades y Eventos",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CalendarView(
                        currentMonth = currentMonth,
                        onMonthChange = { currentMonth = it },
                        selectedDate = selectedDate,
                        onDateSelected = { date ->
                            selectedDate = date
                        },
                        events = events
                    )
                }
            }
        }

        selectedDate?.let { date ->
            val dayEvents = events.filter { it.date == date }

            item {
                RoundedBackground(modifier = Modifier.padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Eventos del ${date.format(DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES")))}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = { showCreateEventDialog = true }
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Añadir evento",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        if (dayEvents.isEmpty()) {
                            Text(
                                "No hay eventos para este día",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            )
                        } else {
                            dayEvents.forEach { event ->
                                EventCard(
                                    event = event,
                                    onDelete = { events = events.filter { it.id != event.id } }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateEventDialog && selectedDate != null) {
        CreateEventDialog(
            date = selectedDate!!,
            onDismiss = { showCreateEventDialog = false },
            onSave = { title, description ->
                events = events + CalendarEvent(
                    date = selectedDate!!,
                    title = title,
                    description = description
                )
                showCreateEventDialog = false
            }
        )
    }
}

@Composable
fun TaskScreen(modifier: Modifier = Modifier) {

    val tasks = remember {
        mutableStateListOf(
            Task(text = "Comprar comida"),
            Task(text = "Llamar al médico"),
            Task(text = "Estudiar Compose"),
            Task(text = "Limpiar la casa"),
            Task(text = "Leer un libro")
        )
    }

    RoundedBackground(
        modifier = modifier.padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Text(
                    "Lista de Tareas",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            item {
                Button(
                    onClick = {
                        tasks.add(Task(text = "Nueva tarea ${tasks.size + 1}"))
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.8f)
                ) {
                    Text("➕ Crear Tarea")
                }
            }

            items(tasks, key = { it.id }) { task ->
                TaskCard(
                    text = task.text,
                    onDelete = {
                        tasks.remove(task)
                    },
                    onTaskUpdated = { newText ->
                        val index = tasks.indexOf(task)
                        if (index != -1) {
                            tasks[index] = task.copy(text = newText)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            RoundedBackground{
                Text("¡Bienvenid@ a las settings", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            HomeCard("¡Bienvenid@ a Housfy, Maria José!")
        }
        item{
            HomeCard("Tienes x tareas pendientes")
        }
    }
}

//----------------------------------------------------------------------------------------------------
//-------------------------------------------- Drawer ------------------------------------------------
//----------------------------------------------------------------------------------------------------

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
){
    ProfileContent(onEditProfile = {onNavigate("profileedit")})
    HorizontalDivider()
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        MenuItem(
            icon = Icons.Default.Home,
            text = "Página de inicio",
            onClick = {onNavigate("home")}
        )
        MenuItem(
            icon = Icons.Default.AddCircle,
            text = "Tareas",
            onClick = {onNavigate("tasks")}
        )
        MenuItem(
            icon = Icons.Default.DateRange,
            text = "Actividades",
            onClick = {onNavigate("activities")}
        )
        MenuItem(
            icon = Icons.Default.ShoppingCart,
            text = "Lista de la compra",
            onClick = {onNavigate("shopping")}
        )
        MenuItem(
            icon = Icons.Default.MailOutline,
            text = "Chat Grupal",
            onClick = {onNavigate("chat")}
        )

        Spacer(modifier = Modifier.weight(1f))
        HorizontalDivider()

        MenuItem(
            icon = Icons.Default.Settings,
            text = "Configuración",
            onClick = {onNavigate("conf")}
        )
    }
}

@Composable
fun TopBar(
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy()
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .size(28.dp)
                    .clickable {
                        onOpenDrawer()
                    }
            )
        },
        title= {
            Text(text = "Housfy")
        }
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    photoUrl: String? = null,
    onEditProfile: () -> Unit = {}
) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = modifier
                            .size(56.dp)
                            .align(Alignment.Bottom)
                    ) {
                        if (photoUrl.isNullOrEmpty()){
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Perfil",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .fillMaxSize()
                            )
                        } else {
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                        Box(
                            modifier = modifier
                                .size(19.dp)
                                .offset(x = 30.dp, y = 30.dp)
                                .padding(2.dp)
                                .background(
                                    color = Color.Green,
                                    shape = CircleShape
                                )
                        )
                    }
                    Text(text = "Maria José", fontSize = 17.sp)

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar perfil",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 8.dp)
                            .clickable {
                                onEditProfile()
                            }
                    )
                }
            }

//----------------------------------------------------------------------------------------------------
//----------------------------------------- Items to use ---------------------------------------------
//----------------------------------------------------------------------------------------------------


data class CalendarEvent(
    val id: String = UUID.randomUUID().toString(),
    val date: LocalDate,
    val title: String,
    val description: String = ""
)

@Composable
fun CalendarView(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    events: List<CalendarEvent>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MonthYearSelector(
            currentMonth = currentMonth,
            onMonthChange = onMonthChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("L", "M", "X", "J", "V", "S", "D").forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val firstDayOfMonth = currentMonth.atDay(1)
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value
        val daysInMonth = currentMonth.lengthOfMonth()

        val weeks = ((firstDayOfWeek + daysInMonth - 1) / 7) + 1

        Column {
            repeat(weeks) { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(7) { dayOfWeek ->
                        val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 2
                        if (dayNumber in 1..daysInMonth) {
                            val date = currentMonth.atDay(dayNumber)
                            val eventCount = events.count { it.date == date }
                            DayCell(
                                day = dayNumber,
                                isSelected = date == selectedDate,
                                isToday = date == LocalDate.now(),
                                eventCount = eventCount,
                                onClick = { onDateSelected(date) }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MonthYearSelector(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
        }

        Text(
            text = currentMonth.format(
                DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es", "ES"))
            ).replaceFirstChar { it.uppercase() },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
        }
    }
}

@Composable
fun RowScope.DayCell(
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    eventCount: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = when {
                    isSelected -> MaterialTheme.colorScheme.primary
                    isToday -> MaterialTheme.colorScheme.primaryContainer
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                color = when {
                    isSelected -> MaterialTheme.colorScheme.onPrimary
                    isToday -> MaterialTheme.colorScheme.onPrimaryContainer
                    else -> MaterialTheme.colorScheme.onSurface
                },
                fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal
            )
            if (eventCount > 0) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    repeat(minOf(eventCount, 3)) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .padding(horizontal = 1.dp)
                                .background(
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreateEventDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Nuevo Evento para ${date.format(DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES")))}")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción (opcional)") },
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
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun EventCard(
    event: CalendarEvent,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (event.description.isNotBlank()) {
                    Text(
                        text = event.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar evento",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)) // esquinas redondeadas
            .background(MaterialTheme.colorScheme.surfaceVariant) // fondo
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun HomeCard(
    text: String,
    modifier: Modifier = Modifier
) {
    RoundedBackground(modifier = modifier.padding(bottom = 8.dp).fillMaxWidth()) {
        Text(text = text, fontSize = 25.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val text: String
)

@Composable
fun TaskCard(
    text: String,
    onDelete: () -> Unit,
    onTaskUpdated: (String) -> Unit,
    modifier: Modifier = Modifier,
    elevation: Dp = 4.dp,
) {
    var isDone by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editedText by rememberSaveable(text) { mutableStateOf(text) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    var offsetX by remember { mutableStateOf(0f) }
    var isDeleting by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

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
                                    // Desliza a la derecha → Eliminar
                                    offsetX > 200f -> {
                                        isDeleting = true
                                        delay(50)
                                        onDelete()
                                    }
                                    // Desliza a la izquierda → Editar
                                    offsetX < -200f -> {
                                        offsetX = 0f
                                        isEditing = true
                                    }
                                    // Vuelve a la posición original si no hizo ninguna acción
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
                    onClick = { isDone = !isDone }
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
                                    if (event.key == Key.Enter && event.type == KeyEventType.KeyDown) {
                                        isEditing = false
                                        focusManager.clearFocus()
                                        if (editedText.trim() != text.trim() && editedText.isNotBlank()) {
                                            onTaskUpdated(editedText.trim())
                                        } else {
                                            editedText = text
                                        }
                                        true
                                    } else false
                                },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent
                            ),
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

@Composable
fun RoundedBackground(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    cornerRadius: Dp = 12.dp,
    content: @Composable () -> Unit // aquí se pasa cualquier contenido
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(12.dp)
    ) {
        content() // aquí se renderiza lo que le pases
    }
}
