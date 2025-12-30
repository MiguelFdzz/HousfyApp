@file:OptIn(ExperimentalMaterial3Api::class)

package fdz.migue.housfyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import fdz.migue.housfyapp.dao.HousfyDatabase
import fdz.migue.housfyapp.dao.activities.CalendarEventRepositoryImpl
import fdz.migue.housfyapp.dao.profile.ProfileRepositoryImpl
import fdz.migue.housfyapp.dao.shopping.ShoppingCartRepositoryImpl
import fdz.migue.housfyapp.dao.tasks.TaskRepositoryImpl
import fdz.migue.housfyapp.features.activities.ActivitiesScreen
import fdz.migue.housfyapp.features.activities.CalendarEventViewModel
import fdz.migue.housfyapp.features.activities.CalendarEventViewModelFactory
import fdz.migue.housfyapp.features.chat.ChatScreen
import fdz.migue.housfyapp.features.home.HomeScreen
import fdz.migue.housfyapp.features.profile.PEditScreen
import fdz.migue.housfyapp.features.profile.ProfileViewModel
import fdz.migue.housfyapp.features.profile.ProfileViewModelFactory
import fdz.migue.housfyapp.features.settings.SettingsScreen
import fdz.migue.housfyapp.features.shopping.ShoppingScreen
import fdz.migue.housfyapp.features.shopping.ShoppingViewModel
import fdz.migue.housfyapp.features.shopping.ShoppingViewModelFactory
import fdz.migue.housfyapp.features.tasks.TaskScreen
import fdz.migue.housfyapp.features.tasks.TaskViewModel
import fdz.migue.housfyapp.features.tasks.TaskViewModelFactory
import fdz.migue.housfyapp.ui.components.TopBar
import fdz.migue.housfyapp.ui.drawer.DrawerContent

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

    val context = LocalContext.current.applicationContext

    val database = remember {
        HousfyDatabase.getDatabase(context)
    }

    val taskRepository = remember {
        TaskRepositoryImpl(database.taskDao())
    }
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(taskRepository)
    )

    val profileRepository = remember {
        ProfileRepositoryImpl(database.profileDao())
    }
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(profileRepository)
    )
    val profile by profileViewModel.profile.collectAsState(initial = null)

    val shoppingRepository = remember {
        ShoppingCartRepositoryImpl(database.shoppingDao())
    }
    val shoppingViewModel: ShoppingViewModel = viewModel(
        factory = ShoppingViewModelFactory(shoppingRepository)
    )

    val calendarRepository = remember {
        CalendarEventRepositoryImpl(database.calendarEventDao())
    }
    val calendarEventViewModel: CalendarEventViewModel = viewModel(
        factory = CalendarEventViewModelFactory(calendarRepository)
    )

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
                    profileName = profile?.name?: "Usuario",
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
                composable("profileedit") {
                    PEditScreen(viewModel = profileViewModel)
                }
                composable("home") {
                    HomeScreen(
                        profileViewModel = profileViewModel,
                        taskViewModel = taskViewModel)
                }
                composable("tasks") {
                    TaskScreen(viewModel = taskViewModel)
                }
                composable("activities") {
                    ActivitiesScreen(viewModel = calendarEventViewModel)
                }
                composable("shopping") {
                    ShoppingScreen(viewModel = shoppingViewModel)
                }
                composable("chat") { ChatScreen() }
                composable("conf") { SettingsScreen() }
            }
        }
    }
}