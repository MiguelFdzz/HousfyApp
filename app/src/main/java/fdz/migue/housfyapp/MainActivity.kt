@file:OptIn(ExperimentalMaterial3Api::class)

package fdz.migue.housfyapp

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fdz.migue.housfyapp.ui.theme.HousfyAppTheme
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

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier){
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent()
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
            ScreenContent(modifier = Modifier.padding(padding))
        }
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier){
    ProfileContent()
    HorizontalDivider()
    MenuItemsContent()
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier){

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
fun ProfileContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Perfil",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(48.dp)
        )
        Text(text = "Miguel", fontSize = 17.sp)
    }
}

@Composable
fun MenuItemsContent(modifier: Modifier = Modifier) {

}

@Preview
@Composable
fun Preview(){
    PantallaPrincipal()
}