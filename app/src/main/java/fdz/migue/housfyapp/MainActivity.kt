@file:OptIn(ExperimentalMaterial3Api::class)

package fdz.migue.housfyapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fdz.migue.housfyapp.dao.ClearDataRepository
import fdz.migue.housfyapp.dao.HousfyDatabase
import fdz.migue.housfyapp.dao.activities.CalendarEventRepositoryImpl
import fdz.migue.housfyapp.dao.chat.ChatRepositoryImpl
import fdz.migue.housfyapp.dao.profile.ProfileRepositoryImpl
import fdz.migue.housfyapp.dao.shopping.ShoppingCartRepositoryImpl
import fdz.migue.housfyapp.dao.tasks.TaskRepositoryImpl
import fdz.migue.housfyapp.features.activities.ActivitiesScreen
import fdz.migue.housfyapp.features.activities.CalendarEventViewModel
import fdz.migue.housfyapp.features.activities.CalendarEventViewModelFactory
import fdz.migue.housfyapp.features.chat.ChatScreen
import fdz.migue.housfyapp.features.chat.ChatViewModel
import fdz.migue.housfyapp.features.chat.ChatViewModelFactory
import fdz.migue.housfyapp.features.home.HomeScreen
import fdz.migue.housfyapp.features.language.LanguageDataStore
import fdz.migue.housfyapp.features.language.LanguageViewModel
import fdz.migue.housfyapp.features.language.LanguageViewModelFactory
import fdz.migue.housfyapp.features.language.updateLocale
import fdz.migue.housfyapp.features.profile.PEditScreen
import fdz.migue.housfyapp.features.profile.ProfileViewModel
import fdz.migue.housfyapp.features.profile.ProfileViewModelFactory
import fdz.migue.housfyapp.features.settings.SettingsScreen
import fdz.migue.housfyapp.features.settings.SettingsViewModel
import fdz.migue.housfyapp.features.settings.SettingsViewModelFactory
import fdz.migue.housfyapp.features.shopping.ShoppingScreen
import fdz.migue.housfyapp.features.shopping.ShoppingViewModel
import fdz.migue.housfyapp.features.shopping.ShoppingViewModelFactory
import fdz.migue.housfyapp.features.tasks.TaskScreen
import fdz.migue.housfyapp.features.tasks.TaskViewModel
import fdz.migue.housfyapp.features.tasks.TaskViewModelFactory
import fdz.migue.housfyapp.ui.components.TopBar
import fdz.migue.housfyapp.ui.drawer.DrawerContent
import fdz.migue.housfyapp.ui.theme.HousfyAppTheme
import kotlinx.coroutines.launch

class LocalizedContextWrapper(
    private val localizedContext: Context,
    private val baseActivity: ComponentActivity
) : ContextWrapper(localizedContext) {

    // Cuando se busque la Activity, devolvemos la original (que tiene el Registry)
    override fun getSystemService(name: String): Any? {
        return when (name) {
            Context.ACTIVITY_SERVICE -> baseActivity
            else -> localizedContext.getSystemService(name)
        }
    }
}
class MainActivity : ComponentActivity() {
    @SuppressLint("ContextCastToActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current.applicationContext
            val languageDataStore = remember { LanguageDataStore(context) }

            val languageViewModel: LanguageViewModel = viewModel(
                factory = LanguageViewModelFactory(languageDataStore)
            )

            val lang by languageViewModel.language.collectAsState()
            val localizedContext = this.updateLocale(lang)

            val wrappedContext = remember(localizedContext) {
                LocalizedContextWrapper(localizedContext, this@MainActivity)
            }

            val activityResultRegistryOwner = LocalActivityResultRegistryOwner.current

            CompositionLocalProvider(
                LocalContext provides wrappedContext,
                LocalActivityResultRegistryOwner provides activityResultRegistryOwner!!
            ) {
                PantallaPrincipal(languageViewModel = languageViewModel)
            }
        }
    }
}

@Composable
fun PantallaPrincipal(
    languageViewModel: LanguageViewModel,
    modifier: Modifier = Modifier
){

    val context = LocalContext.current.applicationContext

    val database = remember {
        HousfyDatabase.getDatabase(context)
    }

    var isDarkTheme by rememberSaveable() { mutableStateOf(false) }
    HousfyAppTheme(darkTheme = isDarkTheme) {

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

        val chatRepository = remember {
            ChatRepositoryImpl(database.chatDao())
        }
        val chatViewModel: ChatViewModel = viewModel(
            factory = ChatViewModelFactory(chatRepository)
        )

        val clearDataRepository = remember {
            ClearDataRepository(database)
        }
        val settingsViewModel: SettingsViewModel = viewModel(
            factory = SettingsViewModelFactory(clearDataRepository)
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
                        viewModel = profileViewModel,
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
                    TopBar(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = {isDarkTheme = !isDarkTheme},
                        onOpenDrawer = {
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
                        PEditScreen(
                            viewModel = profileViewModel,
                        )
                    }
                    composable("home") {
                        HomeScreen(
                            calendarEventViewModel = calendarEventViewModel,
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
                    composable("chat") {
                        ChatScreen(
                            viewModel = chatViewModel,
                            profileViewModel = profileViewModel)
                    }
                    composable("conf") {
                        SettingsScreen(
                            languageViewModel = languageViewModel,
                            onClearData = { settingsViewModel.clearAllData() }
                        )
                    }
                }
            }
        }
    }
    }
