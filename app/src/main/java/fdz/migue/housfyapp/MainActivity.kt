@file:OptIn(ExperimentalMaterial3Api::class)

package fdz.migue.housfyapp

import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

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
                startDestination = "tasks",
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
fun ActivitiesScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            RoundedBackground{
                Text("¡Bienvenid@ a las Actividades", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun TaskScreen(modifier: Modifier = Modifier){
    RoundedBackground(
        modifier = modifier
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item{
                Text("Lista de Tareas", fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            }
            item {
                Button(
                    onClick = { /* TODO: acción para crear tarea */ },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(0.8f) // que no ocupe todo el ancho
                ) {
                    Text("➕ Crear Tarea")
                }
            }
            items(5) { index ->
                TaskCard(
                    text = "Tarea ${index +1}"
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
            RoundedBackground{
                Text("¡Bienvenid@ a Housfy, Maria José!", fontSize = 25.sp, textAlign = TextAlign.Center)
            }
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
fun TaskCard(
    text: String,
    modifier: Modifier = Modifier
) {
    var isDone by remember { mutableStateOf(false) } // estado local

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { isDone = !isDone }, // cambia el estado al clickar
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge.copy(
                textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None
            )
        )
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
            .padding(12.dp) // padding interno opcional
    ) {
        content() // aquí se renderiza lo que le pases
    }
}
